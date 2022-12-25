package com.anubhav.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anubhav.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
