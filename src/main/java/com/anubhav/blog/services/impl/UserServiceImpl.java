package com.anubhav.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anubhav.blog.config.AppConstants;
import com.anubhav.blog.entities.Role;
import com.anubhav.blog.entities.User;
import com.anubhav.blog.exceptions.ResourceNotFoundException;
import com.anubhav.blog.payloads.ForgotPasswordDto;
import com.anubhav.blog.payloads.UserDto;
import com.anubhav.blog.repositories.RoleRepo;
import com.anubhav.blog.repositories.UserRepo;
import com.anubhav.blog.services.UserService;

import net.bytebuddy.asm.Advice.This;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userdto) {
		User user = this.dtoToUser(userdto);
		User saveUser = this.userRepo.save(user);
		return this.userToDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());

		User updatedUser = this.userRepo.save(user);

		UserDto userDto2 = userToDto(updatedUser);

		return userDto2;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.userRepo.findAll();

		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
//		
		User user = modelMapper.map(userDto, User.class);
		return user;
	}

	public UserDto userToDto(User user) {
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
//		 
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);
		System.out.println(newUser);

		return this.modelMapper.map(newUser, UserDto.class);
	}

	@Override
	public ForgotPasswordDto forgotPassword(UserDto userDto, Integer userId, String ans) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		String securityAns = user.getSecurityAns();
		if (securityAns.equals(ans)) {
			user.setPassword(userDto.getPassword());
		} else {
			System.out.println("Answer not match!");
			throw new ResourceNotFoundException("Answer ", "id", userId);
		}

		User updatedUser = this.userRepo.save(user);

		ForgotPasswordDto forgotPasswordDto = modelMapper.map(updatedUser, ForgotPasswordDto.class);
		
		forgotPasswordDto.setAnswer("Password changed successfully for userid : "+userId);
		
		return forgotPasswordDto;

	}

}
