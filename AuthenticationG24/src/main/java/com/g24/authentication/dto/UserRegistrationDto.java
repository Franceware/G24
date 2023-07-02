package com.g24.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRegistrationDto
{
	
	private String confirmEmail;
	//@NotEmpty(message = "Password should not be empty")
	//	@Size (min = 8, message = "Password should have at least 8 characters")
	//	@Pattern(regexp = "^(?=.*[a-z])", message="Password should have at least one lowercase letter")
	//	@Pattern(regexp = "^(?=.*[A-Z])", message="Password should have at least one uppercase letter")
	//	@Pattern(regexp = "^(?=.*[^A-Za-z\s])", message="Password should have at least one number or special character")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z\s]).{8,}", message="Password should have at least eight characters, one lowercase letter, one uppercase letter and one number or special character")
	private String password;

	private String confirmPassword;

}