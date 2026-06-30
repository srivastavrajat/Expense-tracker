package com.rajat.expense_tracker.repository;

import com.rajat.expense_tracker.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {
}
