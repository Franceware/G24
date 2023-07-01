package com.g24.authentication.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.g24.authentication.dto.UserDto;
import com.g24.authentication.service.AuthService;
import com.g24.authentication.service.mapper.MapperUserDto;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthController
{
	@Autowired	private AuthService authService;
	@Autowired  private MapperUserDto mapperUserDto;

	@GetMapping("/index")
	public String index()
	{
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/home")
	public String home() 
	{
		if((SecurityContextHolder.getContext().getAuthentication().getAuthorities()).contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
			return "redirect:/users";
		else
			return "redirect:/home/user";
	}

	@GetMapping("/home/user")
	public String homeUser(Model model)
	{
		return "home"; 
	}

	@GetMapping("/users")
	public String listRegisteredUsers(Model model)
	{
		
		List<UserDto> users = authService.findAllUsers().stream().map(mapperUserDto::toDto).collect(Collectors.toList());
		model.addAttribute("users", users);
		return "users";
	}
}