package com.anubhav.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anubhav.blog.payloads.CommentDto;
import com.anubhav.blog.payloads.PostDto;
import com.anubhav.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentControler {
	
	@Autowired
	private CommentService commentService;

	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto, 
			@PathVariable Integer userId,
			@PathVariable Integer postId) {

		CommentDto creatComment = commentService.creatComment(commentDto, userId, postId);

		return new ResponseEntity<CommentDto>(creatComment, HttpStatus.CREATED);

	}
	
}
