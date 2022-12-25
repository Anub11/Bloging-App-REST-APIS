package com.anubhav.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anubhav.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
