package com.rajat.expense_tracker.specifications;

import com.rajat.expense_tracker.entity.ExpenseEntity;
import org.springframework.data.jpa.domain.Specification;

public class ExpenseSpecification {
    public static Specification<ExpenseEntity> hasMinAmount(Double amount){
        return (
                root,
                query,
                criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("amount"),amount);
    }
    public static Specification<ExpenseEntity> hasDescription(String description){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")),"%"+description.toLowerCase()+"%"));
    }
    public static Specification<ExpenseEntity> hasUserId(Long userId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"),userId);
    }
}
