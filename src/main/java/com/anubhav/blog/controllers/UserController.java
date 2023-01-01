package com.anubhav.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anubhav.blog.payloads.ApiResponse;
import com.anubhav.blog.payloads.UserDto;
import com.anubhav.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired 
	private UserService userService;
	
	//POST - Create User
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto creatUserDto =  this.userService.createUser(userDto);
		return new ResponseEntity<>(creatUserDto,HttpStatus.CREATED);
	}
	
	//PUT - Update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		UserDto updateUserDto = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updateUserDto);
		
	}
	
	//DELETE - Delete User
	@PreAuthorize("hasRole('ADMIN')")  //role based authentication
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
	}
	
	//GET - User get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUserse(){
		return ResponseEntity.ok(userService.getAllUser());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getAllUserse(@PathVariable Integer userId){
		return ResponseEntity.ok(userService.getUserById(userId));
	}
	
	
	
	
	
}
