package com.rajat.expense_tracker.dto.request;

import com.rajat.expense_tracker.entity.UserEntity;

import java.time.LocalDateTime;

public record CreateExpenseRequest(double amount, String description, LocalDateTime createdAt, Long userId) {
}
