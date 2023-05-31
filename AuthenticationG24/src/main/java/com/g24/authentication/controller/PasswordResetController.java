package com.g24.authentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

import com.g24.authentication.service.UserService;
import com.g24.authentication.repository.PasswordResetTokenRepository;
import com.g24.authentication.dto.PasswordResetDto;
import com.g24.authentication.entity.PasswordResetToken;
import com.g24.authentication.entity.User;

@Controller
@RequestMapping("/reset-password")
public class PasswordResetController 
{

    @Autowired private UserService userService;
    @Autowired private PasswordResetTokenRepository tokenRepository;

    @GetMapping
    public String displayResetPasswordPage(@ModelAttribute("passwordResetForm") PasswordResetDto form, @RequestParam String token,Model model) 
    {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        
        if (resetToken == null)
        {
            model.addAttribute("error", "Could not find password reset token.");
        } 
         else if (resetToken.isExpired())
         		{
        	 		model.addAttribute("error", "Token has expired, please request a new password reset.");
         		} 
         		else {
         				model.addAttribute("token", resetToken.getToken());
         		     }
        return "reset-password";
    }

    @PostMapping
    @Transactional
    public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form, BindingResult result, RedirectAttributes redirectAttributes) 
    {

        if (result.hasErrors())
        {
            redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
            redirectAttributes.addFlashAttribute("passwordResetForm", form);
            return  "redirect:/reset-password?token=" + form.getToken();
        }

        PasswordResetToken token = tokenRepository.findByToken(form.getToken());
        User user = token.getUser();
        userService.updatePassword(form.getPassword(), user.getId());
        tokenRepository.delete(token);

        return "redirect:/login?resetSuccess";
    }

}
