package com.rajat.expense_tracker.controller;

import com.rajat.expense_tracker.dto.response.ExpenseSummary;
import com.rajat.expense_tracker.repository.UserRepository;
import com.rajat.expense_tracker.service.ExpenseService;
import com.rajat.expense_tracker.service.security.JwtService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExpenseSummaryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ExpenseService expenseService;
    @MockitoBean
    UserRepository userRepository;
    @MockitoBean
    JwtService jwtService;
    @Test
    void shouldProduceExpenseSummary()throws Exception{
        List<ExpenseSummary> response=new ArrayList<>();
        response.add(new ExpenseSummary(1L,200.0,"Toothpaste"));
        response.add(new ExpenseSummary(2L,600.0,"Bodywash"));
        response.add(new ExpenseSummary(3L,500.0,"Shampoo"));
        when(expenseService.getExpenseSummary()).thenReturn(response);

        mockMvc.perform(get("/expenses/summary")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].amount").value(200.0))
                .andExpect(jsonPath("$[0].description").value("Toothpaste"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].amount").value(600.0))
                .andExpect(jsonPath("$[1].description").value("Bodywash"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].amount").value(500.0))
                .andExpect(jsonPath("$[2].description").value("Shampoo"));
    }

}
