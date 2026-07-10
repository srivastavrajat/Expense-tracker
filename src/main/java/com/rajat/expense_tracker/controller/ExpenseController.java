package com.rajat.expense_tracker.controller;

import com.rajat.expense_tracker.dto.request.CreateExpenseRequest;
import com.rajat.expense_tracker.dto.request.ExpenseSearchRequest;
import com.rajat.expense_tracker.dto.request.UpdateExpenseRequest;
import com.rajat.expense_tracker.dto.response.DeleteResponse;
import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.dto.response.ExpenseSummary;
import com.rajat.expense_tracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService){
        this.expenseService=expenseService;
    }
    @Operation(summary = "Create new expense")
    @PostMapping
    public ExpenseResponse addExpense(@RequestBody CreateExpenseRequest request){
        return expenseService.createExpense(request);
    }
    @Operation(summary = "Get all expenses")
    @GetMapping
    public List<ExpenseResponse> getAllExpense(){
        return expenseService.getAllExpense();
    }
    @Operation(summary = "Get expense by ID")
    @GetMapping("/{id}")
    public ExpenseResponse getExpense(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }
    @Operation(summary = "Update expense by id")
    @PutMapping("/{id}")
    public ExpenseResponse updateExpense(@PathVariable Long id,@RequestBody UpdateExpenseRequest request){
        return expenseService.updateExpense(id,request);
    }
    @Operation(summary = "Delete expense by id")
    @DeleteMapping("/{id}")
    public DeleteResponse deleteExpense(@PathVariable Long id){
        return expenseService.deleteExpense(id);
    }
    @Operation(summary = "Get expenses summary")
    @GetMapping("/summary")
    public List<ExpenseSummary> getSummary(){
        return expenseService.getExpenseSummary();
    }
    @Operation(summary = "search expense by keyword")
    @GetMapping("/search")
    public List<ExpenseResponse> searchExpense(ExpenseSearchRequest request){
        return expenseService.searchExpense(request);
    }
}
