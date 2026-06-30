package com.rajat.expense_tracker.service;

import com.rajat.expense_tracker.dto.request.CreateExpenseRequest;
import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.entity.ExpenseEntity;
import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.exception.UserNotFoundException;
import com.rajat.expense_tracker.repository.ExpenseRepository;
import com.rajat.expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ExpenseService {
    ExpenseRepository expenseRepository;
    UserRepository userRepository;
    public ExpenseService(UserRepository userRepository,ExpenseRepository expenseRepository){
        this.userRepository=userRepository;
        this.expenseRepository=expenseRepository;
    }

    public ExpenseResponse createExpense(CreateExpenseRequest request){
        UserEntity user=userRepository.findById(request.userId())
                .orElseThrow(()->new UserNotFoundException(request.userId()));
        ExpenseEntity expense=new ExpenseEntity();
        expense.setAmount(request.amount());
        expense.setDescription(request.description());
        expense.setCreatedAt(request.createdAt());
        expense.setUser(user);
        ExpenseEntity savedExpense=expenseRepository.save(expense);
        return new ExpenseResponse(
                savedExpense.getId(),
                savedExpense.getAmount(),
                savedExpense.getDescription(),
                savedExpense.getCreatedAt(),
                savedExpense.getUser().getId());
    }
    public List<ExpenseResponse> getAllExpense(){
        List<ExpenseEntity> expenses= expenseRepository.findAll();
        return expenses.stream()
                .map(e -> new ExpenseResponse(
                        e.getId(),
                        e.getAmount(),
                        e.getDescription(),
                        e.getCreatedAt(),
                        e.getUser().getId()))
                .toList();
    }

}
