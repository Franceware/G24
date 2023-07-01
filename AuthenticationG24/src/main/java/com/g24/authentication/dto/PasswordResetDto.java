package com.g24.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PasswordResetDto 
{
    @Size (min = 8, message = "Password should have at least 8 characters")
    private String password;

    @NotEmpty(message = "First name should not be empty")
    private String confirmPassword;

    @NotEmpty
    private String token;
}
