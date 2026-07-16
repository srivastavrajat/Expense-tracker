package com.rajat.expense_tracker.service;

import com.rajat.expense_tracker.dto.request.CreateExpenseRequest;
import com.rajat.expense_tracker.dto.request.ExpenseSearchRequest;
import com.rajat.expense_tracker.dto.request.UpdateExpenseRequest;
import com.rajat.expense_tracker.dto.response.DeleteResponse;
import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.dto.response.ExpenseSummary;
import com.rajat.expense_tracker.entity.ExpenseEntity;
import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.event.ExpenseCreatedEvent;
import com.rajat.expense_tracker.exception.ExpenseNotFoundException;
import com.rajat.expense_tracker.exception.UserNotFoundException;
import com.rajat.expense_tracker.repository.ExpenseRepository;
import com.rajat.expense_tracker.repository.UserRepository;
import com.rajat.expense_tracker.specifications.ExpenseSpecification;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExpenseService {
    private final ExpenseEventProducer expenseEventProducer;
    private static final Logger logger= LoggerFactory.getLogger(ExpenseService.class);
    ExpenseRepository expenseRepository;
    UserRepository userRepository;
    public ExpenseService(UserRepository userRepository,ExpenseRepository expenseRepository,ExpenseEventProducer expenseEventProducer){
        this.userRepository=userRepository;
        this.expenseRepository=expenseRepository;
        this.expenseEventProducer=expenseEventProducer;
    }

    public ExpenseResponse createExpense(CreateExpenseRequest request){
        logger.info("Received request to create expense or userId={}",request.userId());
        UserEntity user=userRepository.findById(request.userId())
                .orElseThrow(()->{
                    logger.error("User not found with id={}",request.userId());
                    return new UserNotFoundException(request.userId());
                });
        logger.debug("User found with id={}", user.getId());

        logger.info("Creating expense for userId={}", user.getId());

        ExpenseEntity expense=new ExpenseEntity();
        expense.setAmount(request.amount());
        expense.setDescription(request.description());
        expense.setCreatedAt(request.createdAt());
        expense.setUser(user);
        ExpenseEntity savedExpense=expenseRepository.save(expense);
        expenseEventProducer.publish(new ExpenseCreatedEvent(savedExpense.getId(),
                savedExpense.getAmount(),
                savedExpense.getDescription(),
                savedExpense.getCreatedAt(),
                savedExpense.getUser().getId()));
        logger.info("Expense created successfully with id={} for userId={}",
                savedExpense.getId(),
                user.getId());
        return new ExpenseResponse(
                savedExpense.getId(),
                savedExpense.getAmount(),
                savedExpense.getDescription(),
                savedExpense.getCreatedAt(),
                savedExpense.getUser().getId());
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
                        e.getUser().getId()))
                .toList();
    }
    @Cacheable(value="expenses",key="#id")
    public ExpenseResponse getExpenseById(Long id) throws ExpenseNotFoundException{
        ExpenseEntity expense=expenseRepository.
                                findById(id).
                                orElseThrow(()->new ExpenseNotFoundException("expense with id ="+id+" not found"));
        return new ExpenseResponse(expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getCreatedAt(),
                expense.getUser().getId());
    }
    @CacheEvict(value="expenses",key="#id")
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
                expense.getUser().getId());
    }
@CacheEvict(value = "expenses",key="#id")
    public DeleteResponse deleteExpense(Long id){
        ExpenseEntity expense=expenseRepository.findById(id).orElseThrow(()->new ExpenseNotFoundException("Expense not found"));
        expenseRepository.delete(expense);
        return new DeleteResponse("Expense with id "+id+" deleted succesfully");
    }
    public List<ExpenseSummary> getExpenseSummary(){
        return expenseRepository.getExpenseSummary();
    }
    public List<ExpenseResponse> searchExpense(ExpenseSearchRequest request){
        Specification<ExpenseEntity> spec=(
                root,
                query,
                criteriaBuilder)
                -> criteriaBuilder.conjunction();
        if(request.minAmount()!=null){
            spec=spec.and(ExpenseSpecification.hasMinAmount(request.minAmount()));
        }
        if(request.description()!=null){
            spec=spec.and(ExpenseSpecification.hasDescription(request.description()));
        }
        if(request.userId()!=null){
            spec=spec.and(ExpenseSpecification.hasUserId(request.userId()));
        }
        List<ExpenseEntity> expenses=expenseRepository.findAll(spec);
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
