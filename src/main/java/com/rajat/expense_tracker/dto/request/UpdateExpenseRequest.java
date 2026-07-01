package com.rajat.expense_tracker.dto.request;

import java.time.LocalDateTime;

public record UpdateExpenseRequest(double amount, String description, LocalDateTime createdAt, Long userId) {
}
