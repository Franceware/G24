package com.g24.authentication.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.g24.authentication.service.AuthService;
import com.g24.authentication.entity.User;
import com.g24.authentication.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService
{
	@Autowired private UserRepository userRepository;
	
	public List<User> findAllUsers() 
	{ 
		return userRepository.findAll();
	}

}


