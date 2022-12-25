package com.anubhav.blog.services;

import java.util.List;

import com.anubhav.blog.payloads.CategortDto;

public interface CategoryService {

//	Create
	CategortDto createCategory(CategortDto categortDto);
//	Update
	CategortDto updateCategory(CategortDto categortDto,Integer catId);
//	Delete
	void deleteCategory(Integer catId);
//	get
	CategortDto getCategory(Integer catId);
//	getAll
	List<CategortDto> getAllCategory();
	
}
