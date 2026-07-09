package com.rajat.expense_tracker.Repository;

import com.rajat.expense_tracker.entity.ExpenseEntity;
import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.repository.ExpenseRepository;
import com.rajat.expense_tracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class ExpenseRepositoryTest {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void shouldSaveExpense(){
        UserEntity user=new UserEntity();
        user.setName("Rajat");
        user.setEmail("rajat@gmail.com");

        ExpenseEntity expense=new ExpenseEntity();
        expense.setAmount(200.0);
        expense.setDescription("Pizza");
        expense.setUser(user);

        ExpenseEntity saved=expenseRepository.save(expense);
        assertNotNull(saved.getId());
        assertEquals("Pizza",saved.getDescription());

    }
}
