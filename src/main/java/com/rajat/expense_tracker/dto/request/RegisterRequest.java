package com.rajat.expense_tracker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Email(message = "Invalid email")
                @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "password cannot be blank")
        String password) {
}
