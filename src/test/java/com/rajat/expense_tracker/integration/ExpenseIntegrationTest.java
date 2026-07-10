package com.rajat.expense_tracker.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajat.expense_tracker.dto.request.CreateExpenseRequest;
import com.rajat.expense_tracker.entity.ExpenseEntity;
import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.repository.ExpenseRepository;
import com.rajat.expense_tracker.repository.UserRepository;
import com.rajat.expense_tracker.service.ExpenseService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ExpenseIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;

    @Test
    void shouldCreateExpense()throws Exception{
        //Arrange
        CreateExpenseRequest request=new CreateExpenseRequest(
                101.0,"Cab", LocalDateTime.now(),8L);
        //convert it to json
        String json= objectMapper.writeValueAsString(request);

        //Now we have the request - call the api /expenses
        long before = expenseRepository.count();
        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        long after = expenseRepository.count();

        assertEquals(before+1,after);

    }
   @Test
    void shouldGetExpenseById()throws Exception{
       UserEntity user = userRepository.findById(8L).orElseThrow();

       ExpenseEntity expense = new ExpenseEntity();
       expense.setAmount(500);
       expense.setDescription("Lunch");
       expense.setCreatedAt(LocalDateTime.now());
       expense.setUser(user);

       ExpenseEntity savedExpense = expenseRepository.save(expense);

       mockMvc.perform(get("/expenses/" + savedExpense.getId())).andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(savedExpense.getId()))
               .andExpect(jsonPath("$.amount").value(500))
               .andExpect(jsonPath("$.description").value("Lunch"))
               .andExpect(jsonPath("$.userId").value(8));

   }
}
