package com.anubhav.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anubhav.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
