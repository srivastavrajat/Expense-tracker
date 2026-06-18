package com.rajat.expense_tracker.dto.request;

public record CreateUserRequest(
        String name,
        String email
    ) {
}
