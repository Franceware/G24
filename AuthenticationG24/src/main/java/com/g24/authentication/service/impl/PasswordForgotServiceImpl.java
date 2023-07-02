package com.g24.authentication.service.impl;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.g24.authentication.service.PasswordForgotService;
import com.g24.authentication.utils.exceptions.TokenException;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import com.g24.authentication.repository.UserRepository;
import com.g24.authentication.repository.TokenRepository;
import com.g24.authentication.entity.User;
import com.g24.authentication.entity.Mail;
import com.g24.authentication.entity.Token;

//Annotazione Service - Questa classe è un servizio Spring che deve essere analizzato dal framework Spring per l'inserimento delle dipendenze.
@Service
public class PasswordForgotServiceImpl implements PasswordForgotService
{
	@Autowired private UserRepository userRepository;
	@Autowired private TokenRepository tokenRepository;
	
	@Autowired private JavaMailSender emailSender;
	@Autowired private SpringTemplateEngine templateEngine;
	
	@Override
	public void generateToken(String email, HttpServletRequest request) throws TokenException
	{
		User user = userRepository.findByEmail(email);
		
        if (user == null)
        {
            throw new TokenException("We could not find an user for that e-mail address.");
        }
        
        Token token = tokenRepository.findByUser(user);
        
        if(token !=null && !token.getType().equals("PasswordReset")) 
        {
        	throw new TokenException("User not enabled.");
        }
        
        if(token != null && token.isExpired())
        {
        	tokenRepository.delete(token);
            token = null;
        }
 
        if (token == null) 
        {        
            token = new Token();
            token.setToken(UUID.randomUUID().toString());
            token.setUser(user);
            token.setType("PasswordReset");
            token.setExpiryDate(30);
            tokenRepository.save(token);
        }
        
        Mail mail = new Mail();
        mail.setType("PasswordReset");
        mail.setFrom("franceware00@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");
 
        Map<String, Object> modelMail = new HashMap<>();
        modelMail.put("token", token);
        modelMail.put("user", user);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        modelMail.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(modelMail);
        sendEmail(mail);
	}
    
	private void sendEmail(Mail mail) throws RuntimeException
	{
		try 
		{
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			Context context = new Context();
			context.setVariables(mail.getModel());
			String html = null;
			switch(mail.getType()) {
			case "Registration":
				html = templateEngine.process("email/reg-email-template", context);
				break;
			case "PasswordReset":
				html = templateEngine.process("email/email-template", context);
				break;
			}

			helper.setTo(mail.getTo());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFrom());

			emailSender.send(message);
		} 
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}