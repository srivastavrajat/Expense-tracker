package com.rajat.expense_tracker.dto.request;

public record ExpenseSearchRequest(Double minAmount,Double maxAmount,String description,Long userId) {
}
