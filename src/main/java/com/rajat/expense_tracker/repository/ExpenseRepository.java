package com.rajat.expense_tracker.repository;

import com.rajat.expense_tracker.dto.response.ExpenseResponse;
import com.rajat.expense_tracker.dto.response.ExpenseSummary;
import com.rajat.expense_tracker.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {
    @Query("""
    SELECT e
    FROM ExpenseEntity e
    JOIN FETCH e.user
""")
    List<ExpenseEntity> getAllExpenses();

    @Query("""
    SELECT new com.rajat.expense_tracker.dto.response.ExpenseSummary(e.id, e.amount, e.description) \
    FROM ExpenseEntity e
    """)
    List<ExpenseSummary> getExpenseSummary();
}
