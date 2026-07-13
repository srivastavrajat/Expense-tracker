package com.rajat.expense_tracker.controller;

import com.rajat.expense_tracker.dto.response.DeleteResponse;
import com.rajat.expense_tracker.repository.UserRepository;
import com.rajat.expense_tracker.service.ExpenseService;
import com.rajat.expense_tracker.service.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(ExpenseController.class)
@ActiveProfiles("test")

@AutoConfigureMockMvc(addFilters = false)
public class DeleteExpenseControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ExpenseService expenseService;
    @MockitoBean
    JwtService jwtService;
    @MockitoBean
    UserRepository userRepository;
    @Test
    void shouldDeleteExpense() throws Exception {
        DeleteResponse response=new DeleteResponse("expense deleted succesfully");

        when(expenseService.deleteExpense(1L)).thenReturn(response);

        mockMvc.perform(delete("/expenses/{id}",1L)).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("expense deleted succesfully"));

    }

}
