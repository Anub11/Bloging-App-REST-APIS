package com.anubhav.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anubhav.blog.entities.Comments;
import com.anubhav.blog.entities.Post;
import com.anubhav.blog.entities.User;
import com.anubhav.blog.exceptions.ResourceNotFoundException;
import com.anubhav.blog.payloads.CommentDto;
import com.anubhav.blog.repositories.CommentRepo;
import com.anubhav.blog.repositories.PostRepo;
import com.anubhav.blog.repositories.UserRepo;
import com.anubhav.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public CommentDto creatComment(CommentDto commentDto, Integer userId, Integer postId) {
		
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));

		Comments comments = modelMapper.map(commentDto, Comments.class);
		
		comments.setPost(post);
		comments.setUser(user);
		
		Comments saveComment = commentRepo.save(comments);
		
		return modelMapper.map(saveComment, CommentDto.class);
	}

}
