package com.rajat.expense_tracker.controller;

import com.rajat.expense_tracker.dto.request.CreateExpenseRequest;
import com.rajat.expense_tracker.dto.request.UpdateExpenseRequest;
import com.rajat.expense_tracker.dto.response.DeleteResponse;
import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.dto.response.ExpenseSummary;
import com.rajat.expense_tracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService){
        this.expenseService=expenseService;
    }
    @PostMapping
    public ExpenseResponse addExpense(@RequestBody CreateExpenseRequest request){
        return expenseService.createExpense(request);
    }
    @GetMapping
    public List<ExpenseResponse> getAllExpense(){
        return expenseService.getAllExpense();
    }
    @GetMapping("/{id}")
    public ExpenseResponse getExpense(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }
    @PutMapping("/{id}")
    public ExpenseResponse updateExpense(@PathVariable Long id,@RequestBody UpdateExpenseRequest request){
        return expenseService.updateExpense(id,request);
    }
    @DeleteMapping("/{id}")
    public DeleteResponse deleteExpense(@PathVariable Long id){
        return expenseService.deleteExpense(id);
    }
    @GetMapping("/summary")
    public List<ExpenseSummary> getSummary(){
        return expenseService.getExpenseSummary();
    }
}
