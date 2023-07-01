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

public class PasswordForgotDto 
{
    
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email has an invalid format")
    private String email;
}
