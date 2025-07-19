package com.jatin.smart_appointment_scheduler.services.operators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LessThanOperatorTest {
    private final LessThanOperator operator = new LessThanOperator();

    @Test
    void returnsFalseIfInputOrCompareIsNull() {
        assertFalse(operator.evaluate(null, 1));
        assertFalse(operator.evaluate(1, null));
    }

    @Test
    void returnsTrueIfInputIsLessThanCompare_Numbers() {
        assertTrue(operator.evaluate(2, 3));
    }

    @Test
    void returnsFalseIfInputIsNotLessThanCompare_Numbers() {
        assertFalse(operator.evaluate(5, 3));
    }

    @Test
    void returnsTrueIfInputIsLessThanCompare_Strings() {
        assertTrue(operator.evaluate("bat", "cat"));
    }

    @Test
    void returnsFalseIfInputIsNotLessThanCompare_Strings() {
        assertFalse(operator.evaluate("cat", "bat"));
    }
} 