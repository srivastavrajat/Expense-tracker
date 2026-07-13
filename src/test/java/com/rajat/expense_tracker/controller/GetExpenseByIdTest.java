package com.rajat.expense_tracker.controller;

import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.repository.UserRepository;
import com.rajat.expense_tracker.service.ExpenseService;
import com.rajat.expense_tracker.service.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(ExpenseController.class)
@ActiveProfiles("test")

@AutoConfigureMockMvc(addFilters = false)

public class GetExpenseByIdTest{
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ExpenseService expenseService;
    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    JwtService jwtService;
    @Test
    void shouldGetExpenseById() throws Exception{
        ExpenseResponse response=new ExpenseResponse(1L,200.0,"Pasta", LocalDateTime.now(),2L);
        when(expenseService.getExpenseById(1L)).thenReturn(response);
        mockMvc.perform(get("/expenses/{id}",1L)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(200.0))
                .andExpect(jsonPath("$.description").value("Pasta"))
                .andExpect(jsonPath("$.userId").value(2L));

    }
}
