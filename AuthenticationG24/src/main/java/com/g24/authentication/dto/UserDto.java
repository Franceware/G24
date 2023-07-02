package com.g24.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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

	private String degreeCourse;

	private String university;
}