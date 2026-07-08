package com.rajat.expense_tracker.controller;

import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.repository.ExpenseRepository;
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
import org.springframework.test.web.servlet.ResultMatcher;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GetAllExpensesTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ExpenseService expenseService;
    @MockitoBean
    UserRepository userRepository;
    @MockitoBean
    private JwtService jwtService;

    @Test
    void shouldGetAllExpense()throws Exception{
        List<ExpenseResponse> responses=new ArrayList<>();
        responses.add(new ExpenseResponse(1L,200.0,"Burger", LocalDateTime.now(),10L));
        responses.add(new ExpenseResponse(2L,300.0,"Pasta", LocalDateTime.now(),11L));
        responses.add(new ExpenseResponse(3L,400.0,"Chips", LocalDateTime.now(),1L));
        responses.add(new ExpenseResponse(4L,500.0,"Dietfood", LocalDateTime.now(),10L));
        when(expenseService.getAllExpense()).thenReturn(responses);
//        mockMvc.perform(get("/expenses")).andExpect(status().isOk()).
//               andExpect(jsonPath("$.length()").value(4));
        mockMvc.perform(get("/expenses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }



}
