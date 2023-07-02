package com.g24.authentication.service;

import com.g24.authentication.utils.exceptions.TokenException;

import jakarta.servlet.http.HttpServletRequest;

public interface PasswordForgotService
{
	void generateToken(String email, HttpServletRequest request) throws TokenException;
}