package com.anubhav.blog.services;

import com.anubhav.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto creatComment(CommentDto commentDto, Integer userId, Integer postId);

}
