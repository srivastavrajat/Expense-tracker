package com.rajat.expense_tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajat.expense_tracker.dto.request.CreateExpenseRequest;
import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.repository.UserRepository;
import com.rajat.expense_tracker.service.ExpenseService;
import com.rajat.expense_tracker.service.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.time.LocalDateTime;


@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ExpenseService expenseService;
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    void shouldCreateExpenseSuccessfully() throws Exception {
        CreateExpenseRequest request=new CreateExpenseRequest(500.0,"Pizza",LocalDateTime.now(),10L);
        ExpenseResponse response = new ExpenseResponse(10L, 500, "Pizza", LocalDateTime.now(), 1L);
        when(expenseService.createExpense(any(CreateExpenseRequest.class)))
                .thenReturn(response);
        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }
}
