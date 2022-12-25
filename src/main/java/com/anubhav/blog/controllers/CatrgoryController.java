package com.anubhav.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anubhav.blog.payloads.ApiResponse;
import com.anubhav.blog.payloads.CategortDto;
import com.anubhav.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CatrgoryController {

	@Autowired
	private CategoryService categoryService;
	
//	create
	@PostMapping("/")
	public ResponseEntity<CategortDto> createCategory(@Valid @RequestBody CategortDto categortDto){
		CategortDto createCategory = categoryService.createCategory(categortDto);
		return new ResponseEntity<CategortDto>(createCategory,HttpStatus.CREATED);
	}
//	update
	@PutMapping("/{catId}")
	public ResponseEntity<CategortDto> updateCategory(@Valid @RequestBody CategortDto categortDto,@PathVariable Integer catId){
		CategortDto updateCategory = categoryService.updateCategory(categortDto, catId);
		return new ResponseEntity<CategortDto>(updateCategory,HttpStatus.OK);
	}
//	delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted",true),HttpStatus.OK);
	}
//	get
	@GetMapping("/{catId}")
	public ResponseEntity<CategortDto> getCategory(@PathVariable Integer catId){
		CategortDto getCategory = categoryService.getCategory(catId);
		return new ResponseEntity<CategortDto>(getCategory,HttpStatus.OK);
	}
	
//	getAll
	@GetMapping("/")
	public ResponseEntity<List<CategortDto>> getAllCategory(){
		List<CategortDto> allCategory = categoryService.getAllCategory();
		return ResponseEntity.ok(allCategory);
	}
}
