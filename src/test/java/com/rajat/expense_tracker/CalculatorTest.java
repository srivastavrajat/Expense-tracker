package com.rajat.expense_tracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void shouldAddTwoNumbers(){
        int result=5+3;
        assertEquals(8,result);
    }
}
