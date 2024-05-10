package com.books.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
        @NotBlank(message = "First name must not be blank")
        String firstName,
        @NotBlank(message = "Last name must not be blank")
        String lastName,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email must not be blank")
        String email,
        @NotBlank(message = "Password must not be blank")
        String password,
        @NotBlank(message = "Role must not be blank")
        String role
) {

}
