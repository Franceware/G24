package com.g24.authentication.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;
import java.util.UUID;
//import java.util.stream.Collectors;

import com.g24.authentication.service.RegisterService;
//import com.g24.authentication.service.UserService;
//import com.g24.authentication.service.TokenService;
import com.g24.authentication.utils.exceptions.EmailException;
import com.g24.authentication.utils.exceptions.TokenException;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import com.g24.authentication.repository.UserRepository;
import com.g24.authentication.repository.RoleRepository;
import com.g24.authentication.repository.TokenRepository;
//import com.g24.authentication.dto.UserDto;
import com.g24.authentication.entity.User;
import com.g24.authentication.entity.Mail;
import com.g24.authentication.entity.Role;
import com.g24.authentication.entity.Token;

//Annotazione Service - Questa classe è un servizio Spring che deve essere analizzato dal framework Spring per l'inserimento delle dipendenze.
@Service
public class RegisterServiceImpl implements RegisterService
{
	@Autowired private UserRepository userRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private TokenRepository tokenRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	//Legate all'invio della mail
	@Autowired private JavaMailSender emailSender;

	@Autowired private SpringTemplateEngine templateEngine;

	@Override
	public User findUserByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}	

	//saveUser - Crea una nuova entità per salvarla nel DB
	@Override
	public void saveUser(User user, String password, HttpServletRequest request) throws EmailException
	{		
		User existingUser = findUserByEmail(user.getEmail());
		
		if(existingUser != null)
		{
			Token token = tokenRepository.findByUser(existingUser);
			
			if(token != null) 
			{
				if (token.getType().equals("Registration") && token.isExpired()) 
				{
					tokenRepository.delete(token);
					deleteUser(existingUser);
				}
			}
			else
			{
				throw new EmailException("There is already an account registered with the same email");
			}
		}
		
		//Crittografia della password utilizzando Spring Security
		user.setPassword(passwordEncoder.encode(password));
		
		Role role = roleRepository.findByName("ROLE_USER");
		
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
		
		Token token = new Token();
		token.setToken(UUID.randomUUID().toString());
		token.setUser(user);
		token.setType("Registration");
		token.setExpiryDate(30);
        tokenRepository.save(token);

		Mail mail = new Mail();
		mail.setType("Registration");
		mail.setFrom("franceware00@gmail.com");
		mail.setTo(user.getEmail());
		mail.setSubject("Registration request");

		Map<String, Object> modelMail = new HashMap<>();
		modelMail.put("token", token);
		modelMail.put("user", user);
	
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		modelMail.put("resetUrl", url + "/confirm-registration?token=" + token.getToken());
		mail.setModel(modelMail);
		sendEmail(mail);
	}
	
    @Override
    public void enableUser(String token) throws TokenException
    {
    	Token resetToken = findByToken(token);	

		if (resetToken == null)
		{
			throw new TokenException("Could not find registration token.");
		}
		else 
			if (!resetToken.getType().equals("Registration"))
			{
				throw new TokenException("Token can't be used for registration.");
			}
			else {
		    		tokenRepository.delete(resetToken);
		    		userRepository.enable(resetToken.getUser().getId());
			}
    }
    
    public Token findByToken(String token) {

        return tokenRepository.findByToken(token);

    }
    
	@Override
	public void deleteUser(User user) 
	{
		userRepository.delete(user);
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