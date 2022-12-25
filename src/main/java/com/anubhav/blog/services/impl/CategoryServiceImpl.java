package com.anubhav.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anubhav.blog.entities.Category;
import com.anubhav.blog.exceptions.ResourceNotFoundException;
import com.anubhav.blog.payloads.CategortDto;
import com.anubhav.blog.repositories.CategoryRepo;
import com.anubhav.blog.services.CategoryService;

@Service
class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategortDto createCategory(CategortDto categortDto) {
		// TODO Auto-generated method stub
		Category cat = modelMapper.map(categortDto, Category.class);
		Category addedCategory = categoryRepo.save(cat);
		return modelMapper.map(addedCategory, CategortDto.class);
	}

	@Override
	public CategortDto updateCategory(CategortDto categortDto, Integer catId) {
		// TODO Auto-generated method stub
		Category cat = categoryRepo.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Categor", "Category Id", catId));
		cat.setCategoryTitle(categortDto.getCategoryTitle());
		cat.setCategoryDesc(categortDto.getCategoryDesc());
		
		Category updateCategory = categoryRepo.save(cat);
		
		return modelMapper.map(updateCategory, CategortDto.class);
	}

	@Override
	public void deleteCategory(Integer catId) {
		// TODO Auto-generated method stub
		Category cat = categoryRepo.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Categor", "Category Id", catId));
		categoryRepo.delete(cat);

	}

	@Override
	public CategortDto getCategory(Integer catId) {
		// TODO Auto-generated method stub
		Category cat = categoryRepo.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Categor", "Category Id", catId));

		return modelMapper.map(cat, CategortDto.class);
	}
	
	

	@Override
	public List<CategortDto> getAllCategory() {
		List<Category> findAllCategory = categoryRepo.findAll();
		List<CategortDto> categortdto = findAllCategory.stream().map(e->modelMapper.map(e, CategortDto.class)).collect(Collectors.toList());
		return categortdto;
	}

}
