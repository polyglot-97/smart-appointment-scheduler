package com.jatin.smart_appointment_scheduler.services.operators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GreaterThanOperatorTest {
    private final GreaterThanOperator operator = new GreaterThanOperator();

    @Test
    void returnsFalseIfInputOrCompareIsNull() {
        assertFalse(operator.evaluate(null, 1));
        assertFalse(operator.evaluate(1, null));
    }

    @Test
    void returnsTrueIfInputIsGreaterThanCompare_Numbers() {
        assertTrue(operator.evaluate(5, 3));
    }

    @Test
    void returnsFalseIfInputIsNotGreaterThanCompare_Numbers() {
        assertFalse(operator.evaluate(2, 3));
    }

    @Test
    void returnsTrueIfInputIsGreaterThanCompare_Strings() {
        assertTrue(operator.evaluate("cat", "bat"));
    }

    @Test
    void returnsFalseIfInputIsNotGreaterThanCompare_Strings() {
        assertFalse(operator.evaluate("bat", "cat"));
    }
} 