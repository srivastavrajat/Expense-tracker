package com.rajat.expense_tracker.dto.response;

import com.rajat.expense_tracker.enums.Role;

public record RegisterResponse(Long id, String name, String email, Role role) {
}
