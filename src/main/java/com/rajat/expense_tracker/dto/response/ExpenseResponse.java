package com.rajat.expense_tracker.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseResponse(
        Long id,
        double amount,
        String description,
        LocalDateTime date,
        Long userId){}
        //String userName