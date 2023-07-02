package com.g24.authentication.service;

import java.util.List;

import com.g24.authentication.entity.User;

public interface AuthService 
{
	boolean access();
	
	List<User> findAllUsers();
}
