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
  
import com.g24.authentication.service.PasswordForgotService;
import com.g24.authentication.utils.exceptions.TokenException;
import com.g24.authentication.dto.PasswordForgotDto;

 
@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController
{
	@Autowired private PasswordForgotService passwordForgotService;
   
 
    @GetMapping
    public String displayForgotPasswordPage(@ModelAttribute("forgotPasswordForm") PasswordForgotDto form) {
        return "forgot-password";
    }
 
    @PostMapping
    public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
            BindingResult result,
            HttpServletRequest request) {
 
    	try
    	{
    		passwordForgotService.generateToken(form.getEmail(), request);
    	}
    	catch(TokenException tokenException)
    	{
        	result.rejectValue("email", null, tokenException.getMessage());
        	
    	}
        
        if (result.hasErrors()){
            return "forgot-password";
        }
        
        return "redirect:/forgot-password?success";
    }
 
}
 