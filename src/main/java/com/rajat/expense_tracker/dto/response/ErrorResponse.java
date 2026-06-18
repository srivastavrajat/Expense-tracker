package com.rajat.expense_tracker.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp,String message, int status) {
}
