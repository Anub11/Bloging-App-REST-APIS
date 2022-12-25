package com.anubhav.blog.services;

import java.util.List;


import com.anubhav.blog.entities.Category;
import com.anubhav.blog.entities.Post;
import com.anubhav.blog.entities.User;
import com.anubhav.blog.payloads.CategortDto;
import com.anubhav.blog.payloads.PostDto;
import com.anubhav.blog.payloads.PageResponse;

public interface PostService {
	
//	Create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
//	Update
	PostDto updatePost(PostDto postDto,Integer postId);
//	Delete
	void deletePost(Integer postId);
//	get
	PostDto getPost(Integer postId);
//	getAll
	List<PostDto> getAllPost();
	
//	Pagicnation
	List<PostDto> postPagicnation(Integer pageNumber,Integer pageSize);

//	PagicnationWithResponse
	PageResponse postPagicnationWithResponse(Integer pageNumber,Integer pageSize, String sorty,String sortDir);

	
	
//	get post by User
	List<PostDto> getPostByUser(Integer userId);
//	get post by category
	List<PostDto> getPostByCategort(Integer categoryId);
//  get post by search
	List<PostDto> getPostBySearch(String keyword);

}
