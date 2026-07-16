package com.rajat.expense_tracker.service;

import com.rajat.expense_tracker.dto.request.CreateExpenseRequest;
import com.rajat.expense_tracker.dto.request.ExpenseSearchRequest;
import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.entity.ExpenseEntity;
import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.exception.UserNotFoundException;
import com.rajat.expense_tracker.repository.ExpenseRepository;
import com.rajat.expense_tracker.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseEventProducer expenseEventProducer;

    @InjectMocks
    private ExpenseService expenseService;
    @Test
   void shouldCreateExpenseSuccesfully(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Rajat");

        CreateExpenseRequest request =
                new CreateExpenseRequest(
                        500.0,
                        "Pizza",
                        LocalDateTime.now(),
                        1L
                );
        //Arrange
        ExpenseEntity savedExpense = new ExpenseEntity();
        savedExpense.setId(10L);
        savedExpense.setAmount(500.0);
        savedExpense.setDescription("Pizza");
        savedExpense.setCreatedAt(request.createdAt());
        savedExpense.setUser(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(expenseRepository.save(any(ExpenseEntity.class))).thenReturn(savedExpense);

        //Act
        ExpenseResponse response=expenseService.createExpense(request);

        //Assert
        assertEquals(10L, response.id());
        assertEquals(500.0,response.amount());
        assertEquals("Pizza",response.description());
        assertEquals(1L,response.userId());


        verify(userRepository).findById(1L);
        verify(expenseRepository).save(any(ExpenseEntity.class));

   }
   @Test
    void shouldThrowExceptionWhenUserDoesNotExist(){
        //Arrange
       CreateExpenseRequest request=new CreateExpenseRequest(
               500.0,
               "Chips",
               LocalDateTime.now(),
               1L
       );
       when(userRepository.findById(1L)).thenReturn(Optional.empty());
       assertThrows(UserNotFoundException.class,()->expenseService.createExpense(request));
       verify(expenseRepository,never()).save(any(ExpenseEntity.class));

   }
    @Test
    void shouldCreateExpenseSuccesfullyCheck() {
        UserEntity user=new UserEntity();
        user.setId(1L);
        user.setName("Rajat");
        //Arrange
        CreateExpenseRequest request = new CreateExpenseRequest(
                500.0,
                "Chips",
                LocalDateTime.now(),
                1L
        );
       when(userRepository.findById(1L)).thenReturn(Optional.of(user));
       // assertThrows(UserNotFoundException.class, () -> expenseService.createExpense(request));
        when(expenseRepository.save(any(ExpenseEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));;
        ExpenseResponse response=expenseService.createExpense(request);
        assertEquals(500.0, response.amount());
        assertEquals("Chips", response.description());
        assertEquals(1L, response.userId());

        ArgumentCaptor<ExpenseEntity> captor = ArgumentCaptor.forClass(ExpenseEntity.class);

        verify(expenseRepository).save(captor.capture());
        ExpenseEntity capturedExpense = captor.getValue();

        assertEquals(500.0, capturedExpense.getAmount());
        assertEquals("Chips", capturedExpense.getDescription());
        assertEquals(1L, capturedExpense.getUser().getId());
    }

}
