package com.rajat.expense_tracker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Email(message = "Email invalid")
         @NotBlank(message = "Email cannot be blank")
        String email
    ) {
}
