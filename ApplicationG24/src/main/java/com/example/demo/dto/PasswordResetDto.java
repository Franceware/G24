package com.example.demo.dto;

import com.example.demo.constraints.FieldMatch;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class PasswordResetDto {

    //@NotEmpty
    @Size (min = 8, message = "Password should have at least 8 characters")
    private String password;

    @NotEmpty(message = "First name should not be empty")
    private String confirmPassword;

    @NotEmpty
    private String token;
}
