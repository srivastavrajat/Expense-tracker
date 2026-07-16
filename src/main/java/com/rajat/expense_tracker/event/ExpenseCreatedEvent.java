package com.rajat.expense_tracker.event;

import java.time.LocalDateTime;

public record ExpenseCreatedEvent(Long expenseId,
                                  Double amount,
                                  String description,
                                  LocalDateTime createdAt,
                                  Long userId) {
}
