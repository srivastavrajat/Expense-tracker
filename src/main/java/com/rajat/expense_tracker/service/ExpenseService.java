package com.rajat.expense_tracker.service;

import com.rajat.expense_tracker.dto.request.CreateExpenseRequest;
import com.rajat.expense_tracker.dto.request.UpdateExpenseRequest;
import com.rajat.expense_tracker.dto.response.DeleteResponse;
import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.dto.response.ExpenseSummary;
import com.rajat.expense_tracker.entity.ExpenseEntity;
import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.exception.ExpenseNotFoundException;
import com.rajat.expense_tracker.exception.UserNotFoundException;
import com.rajat.expense_tracker.repository.ExpenseRepository;
import com.rajat.expense_tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                savedExpense.getUser().getName());
    }
    @Transactional
    public List<ExpenseResponse> getAllExpense(){
        List<ExpenseEntity> expenses= expenseRepository.getAllExpenses();
        return expenses.stream()
                .map(e -> new ExpenseResponse(
                        e.getId(),
                        e.getAmount(),
                        e.getDescription(),
                        e.getCreatedAt(),
                        e.getUser().getName()))
                .toList();
    }
    public ExpenseResponse getExpenseById(Long id) throws ExpenseNotFoundException{
        ExpenseEntity expense=expenseRepository.
                                findById(id).
                                orElseThrow(()->new ExpenseNotFoundException("expense with id ="+id+" not found"));
        return new ExpenseResponse(expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getCreatedAt(),
                expense.getUser().getName());
    }
    @Transactional
    public ExpenseResponse updateExpense(Long id, UpdateExpenseRequest request){
        UserEntity user=userRepository.findById(request.userId()).orElseThrow(()->new UserNotFoundException(request.userId()));
        ExpenseEntity expense=expenseRepository.
                findById(id).
                orElseThrow(()->new ExpenseNotFoundException("expense with id ="+id+" not found"));
        expense.setAmount(request.amount());
        expense.setDescription(request.description());
        expense.setCreatedAt(LocalDateTime.now());;
        expense.setUser(user);
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getCreatedAt(),
                expense.getUser().getName());
    }

    public DeleteResponse deleteExpense(Long id){
        ExpenseEntity expense=expenseRepository.findById(id).orElseThrow(()->new ExpenseNotFoundException("Expense not found"));
        expenseRepository.delete(expense);
        return new DeleteResponse("Expense with id "+id+" deleted succesfully");
    }
    public List<ExpenseSummary> getExpenseSummary(){
        return expenseRepository.getExpenseSummary();
    }

}
