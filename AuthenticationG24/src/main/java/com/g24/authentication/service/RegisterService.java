package com.g24.authentication.service;

import com.g24.authentication.entity.User;
import com.g24.authentication.entity.Token;

import com.g24.authentication.utils.exceptions.EmailException;
import com.g24.authentication.utils.exceptions.TokenException;

import jakarta.servlet.http.HttpServletRequest;

public interface RegisterService
{
	User findUserByEmail(String email);
	
	Token findByToken(String token);

	void saveUser(User user, String password, HttpServletRequest request) throws EmailException;
	
	void deleteUser(User user);
    
    void enableUser(String token) throws TokenException;
}