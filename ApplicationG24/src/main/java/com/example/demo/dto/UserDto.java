package com.example.demo.dto;

import com.example.demo.constraints.FieldMatch;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@FieldMatch.List({
	@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
	@FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class UserDto
{
	private Long id;
	@NotEmpty(message = "First name should not be empty")
	private String firstName;
	@NotEmpty(message = "Last name should not be empty")
	private String lastName;
	
	@NotEmpty(message = "Email should not be empty")
	@Email(message = "Email has an invalid format")
	private String email;

	private String confirmEmail;
	//@NotEmpty(message = "Password should not be empty")
	//	@Size (min = 8, message = "Password should have at least 8 characters")
	//	@Pattern(regexp = "^(?=.*[a-z])", message="Password should have at least one lowercase letter")
	//	@Pattern(regexp = "^(?=.*[A-Z])", message="Password should have at least one uppercase letter")
	//	@Pattern(regexp = "^(?=.*[^A-Za-z\s])", message="Password should have at least one number or special character")
	//@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z\s]).{8,}", message="Password should have at least eight characters, one lowercase letter, one uppercase letter and one number or special character")
	private String password;

	private String confirmPassword;

	private String degreeCourse;

	private String university;
}