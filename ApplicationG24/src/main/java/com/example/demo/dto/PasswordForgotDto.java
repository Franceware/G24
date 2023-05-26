package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
