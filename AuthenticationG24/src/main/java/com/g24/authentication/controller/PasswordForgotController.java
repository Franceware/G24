package com.g24.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.g24.authentication.entity.Mail;
import com.g24.authentication.service.EmailService;
import com.g24.authentication.service.UserService;
import com.g24.authentication.dto.PasswordForgotDto;
import com.g24.authentication.entity.PasswordResetToken;
import com.g24.authentication.entity.User;
import com.g24.authentication.repository.PasswordResetTokenRepository;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController 
{
	@Autowired private UserService userService;
	@Autowired private PasswordResetTokenRepository tokenRepository;
	@Autowired private EmailService emailService;

	@GetMapping
	public String displayForgotPasswordPage(@ModelAttribute("forgotPasswordForm") PasswordForgotDto form) {
		return "forgot-password";
	}

	@PostMapping
	public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
			BindingResult result,
			HttpServletRequest request) {

		User user = userService.findUserByEmail(form.getEmail());
		if (user == null){
			result.rejectValue("email", null, "We could not find an account for that e-mail address.");
		}

		if (result.hasErrors()){
			return "forgot-password";
		}
		
		PasswordResetToken token = tokenRepository.findByUser(user);
		
		if(token != null && token.isExpired()){
			tokenRepository.delete(token);
			token = null;
		}

		if (token == null) {		
			token = new PasswordResetToken();
			token.setToken(UUID.randomUUID().toString());
			token.setUser(user);
			token.setExpiryDate(30);
			tokenRepository.save(token);
		}
		
		Mail mail = new Mail();
		mail.setFrom("franceware00@gmail.com");
		mail.setTo(user.getEmail());
		mail.setSubject("Password reset request");

		Map<String, Object> modelMail = new HashMap<>();
		modelMail.put("token", token);
		modelMail.put("user", user);
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		modelMail.put("resetUrl", url + "/reset-password?token=" + token.getToken());
		mail.setModel(modelMail);
		emailService.sendEmail(mail);
		System.out.print(request);
		return "redirect:/forgot-password?success";

	}

}
