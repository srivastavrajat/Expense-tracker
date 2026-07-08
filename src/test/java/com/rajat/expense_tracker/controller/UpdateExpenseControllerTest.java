package com.rajat.expense_tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajat.expense_tracker.dto.request.UpdateExpenseRequest;
import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.repository.UserRepository;
import com.rajat.expense_tracker.service.ExpenseService;
import com.rajat.expense_tracker.service.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UpdateExpenseControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ExpenseService expenseService;
    @MockitoBean
    UserRepository userRepository;
    @MockitoBean
    JwtService jwtService;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    void shouldUpdateExpense() throws Exception{
        UpdateExpenseRequest request=new UpdateExpenseRequest(200.0,"Mangos", LocalDateTime.now(),1L);
        ExpenseResponse response=new ExpenseResponse(1L,200,"Mangos",LocalDateTime.now(),1L);
        when(expenseService.updateExpense(anyLong(),any(UpdateExpenseRequest.class))).thenReturn(response);
        mockMvc.perform(
                put( "/expenses/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(200.0))
                .andExpect(jsonPath("$.description").value("Mangos"))
                .andExpect(jsonPath("$.userId").value(1L));
    }

}
