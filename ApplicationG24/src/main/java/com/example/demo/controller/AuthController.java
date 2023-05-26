package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController
{
	private UserService userService;

	public AuthController(UserService userService)
	{
		this.userService = userService;
	}

	@GetMapping("/index")
	public String index()
	{
		return "index";
	}

	@GetMapping("/register")
	public String showRegistrationForm(@ModelAttribute("user") UserDto userDto, Model model)
	{
		return "register";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/home")
	public String home(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		User user = userService.findUserByEmail(username);
		model.addAttribute("user", user);
		return "home";
	}

	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model)
	{
		User existingUser = userService.findUserByEmail(userDto.getEmail());

		if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty())
		{
			result.rejectValue("email", null, "C'è già un account registrato con la stessa email");
		}

		if(result.hasErrors())
		{
			return "/register";
		}

		userService.saveUser(userDto);
		return "redirect:/login?registerSuccess";
	}

	@GetMapping("/users")
	public String listRegisteredUsers(Model model)
	{
		List<UserDto> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "users";
	}
}