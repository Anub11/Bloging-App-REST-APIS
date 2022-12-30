package com.anubhav.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anubhav.blog.entities.Comments;

public interface CommentRepo extends JpaRepository<Comments, Integer> {

}
