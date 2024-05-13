package com.books.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

        @NotBlank(message = "First name must not be blank")
        private String firstName;

        @NotBlank(message = "Last name must not be blank")
        private String lastName;

        @Email(message = "Please enter a valid email address.")
        @NotBlank(message = "Email must not be blank")
        private String email;

        @NotBlank(message = "Password must not be blank")
        private String password;

        @NotBlank(message = "Token must not be blank")
        private String token;
}
