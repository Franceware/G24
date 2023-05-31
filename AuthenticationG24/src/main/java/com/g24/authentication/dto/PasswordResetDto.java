package com.g24.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import com.g24.authentication.constraints.FieldMatch;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class PasswordResetDto 
{
    @Size (min = 8, message = "Password should have at least 8 characters")
    private String password;

    @NotEmpty(message = "First name should not be empty")
    private String confirmPassword;

    @NotEmpty
    private String token;
}
