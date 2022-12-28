package com.anubhav.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anubhav.blog.entities.Category;
import com.anubhav.blog.entities.Post;
import com.anubhav.blog.entities.User;
import com.anubhav.blog.exceptions.ResourceNotFoundException;
import com.anubhav.blog.payloads.PageResponse;
import com.anubhav.blog.payloads.PostDto;
import com.anubhav.blog.repositories.CategoryRepo;
import com.anubhav.blog.repositories.PostRepo;
import com.anubhav.blog.repositories.UserRepo;
import com.anubhav.blog.services.PostService;


@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId ) {
		// TODO Auto-generated method stub
		
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		Category cat = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Categor", "Category Id", categoryId));

		
		Post posts = modelMapper.map(postDto, Post.class);
		posts.setImageName("default.png");
		posts.setAddedDate(new Date());
		posts.setUser(user);
		posts.setCategory(cat);
		
		Post savePosts = postRepo.save(posts);
		
		
		return modelMapper.map(savePosts, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post savePost = postRepo.save(post);
		return modelMapper.map(savePost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		postRepo.delete(post);
	}

	@Override
	public PostDto getPost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));

		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> allPost = postRepo.findAll();
		List<PostDto> posts = allPost.stream().map(e->modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		return posts;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		List<Post> posts = postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((e)-> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		return postDtos;		
	}

	@Override
	public List<PostDto> getPostByCategort(Integer categoryId) {
		Category cat = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Categor", "Category Id", categoryId));
		List<Post> posts = postRepo.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map((e)-> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostBySearch(String keyword) {
		List<Post> post = postRepo.findByTitleContaining(keyword);
		List<PostDto> posts = post.stream().map(e->modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		return posts;
	}

	@Override
	public List<PostDto> postPagicnation(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		Page<Post> PagePost = postRepo.findAll(pageable);
		List<Post> allPost = PagePost.getContent();
		List<PostDto> posts = allPost.stream().map(e->modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		return posts;
		
	}

	@Override
	public PageResponse postPagicnationWithResponse(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
//		Pageable pageable = PageRequest.of(pageNumber,pageSize);
//		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(shortBy));
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> PagePost = postRepo.findAll(pageable);
		List<Post> allPost = PagePost.getContent();
		List<PostDto> posts = allPost.stream().map(e->modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		
		PageResponse pageRespons = new PageResponse();
		
		pageRespons.setContent(posts);
		pageRespons.setPageNumber(PagePost.getNumber());
		pageRespons.setPageSize(PagePost.getSize());
		pageRespons.setTotalElements(PagePost.getTotalElements());
		pageRespons.setTotalPages(PagePost.getTotalPages());
		pageRespons.setLastPage(PagePost.isLast());
		
		return pageRespons;
	}


}
