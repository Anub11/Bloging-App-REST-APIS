package com.anubhav.blog.payloads;

import java.util.Date;

import javax.persistence.ManyToOne;

import com.anubhav.blog.entities.Category;
import com.anubhav.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	
	private String title;

	private String content;

	private String imageName;
	
	private Date addedDate;

	private CategortDto category;

	private UserDto user;

}
