package com.anubhav.blog.services;

import java.util.List;

import com.anubhav.blog.payloads.ForgotPasswordDto;
import com.anubhav.blog.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUser();
	void deleteUser(Integer userId);
	UserDto registerNewUser(UserDto user);
	ForgotPasswordDto forgotPassword(UserDto user,Integer userId, String ans);
	
	
}
