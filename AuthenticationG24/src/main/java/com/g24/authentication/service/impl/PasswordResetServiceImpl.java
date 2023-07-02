package com.g24.authentication.service.impl;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.g24.authentication.service.PasswordResetService;
import com.g24.authentication.utils.exceptions.PasswordException;
import com.g24.authentication.utils.exceptions.TokenException;

import com.g24.authentication.repository.UserRepository;
import com.g24.authentication.repository.TokenRepository;
import com.g24.authentication.entity.User;
import com.g24.authentication.entity.Token;

//Annotazione Service - Questa classe è un servizio Spring che deve essere analizzato dal framework Spring per l'inserimento delle dipendenze.
@Service
public class PasswordResetServiceImpl implements PasswordResetService
{
	@Autowired private UserRepository userRepository;
	@Autowired private TokenRepository tokenRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
    @Override

    public String findByToken(String token) throws TokenException
    {
    	Token resetToken = tokenRepository.findByToken(token);
    	
    	if(resetToken == null)
    	{
    		throw new TokenException("Could not find password reset token.");
    	}
    	else
    	{
    		if (!resetToken.getType().equals("PasswordReset"))
    		{
    			throw new TokenException("User not enabled.");
    		}
    		else if (resetToken.isExpired())
			{
    			throw new TokenException("Token has expired, please request a new password reset.");
			}
			else {
				return resetToken.getToken();
			}
    	}
		
    }
    
	public void passwordReset(String token, String newPassword) throws PasswordException
	{
		Token resetToken = tokenRepository.findByToken(token);
		
		User user = resetToken.getUser();
	
		String passwordEncrypt = passwordEncoder.encode(newPassword);


		if (passwordEncoder.matches(newPassword, user.getPassword()))
		{
			throw new PasswordException("New password can't be equal to the old one");
		}

		userRepository.updatePassword(passwordEncrypt, user.getId());

		tokenRepository.delete(resetToken);
	}
}