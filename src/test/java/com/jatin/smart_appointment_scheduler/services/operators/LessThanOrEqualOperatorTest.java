package com.jatin.smart_appointment_scheduler.services.operators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LessThanOrEqualOperatorTest {
    private final LessThanOrEqualOperator operator = new LessThanOrEqualOperator();

    @Test
    void returnsFalseIfInputOrCompareIsNull() {
        assertFalse(operator.evaluate(null, 1));
        assertFalse(operator.evaluate(1, null));
    }

    @Test
    void returnsTrueIfInputIsLessThanOrEqualCompare_Numbers() {
        assertTrue(operator.evaluate(2, 3));
        assertTrue(operator.evaluate(3, 3));
    }

    @Test
    void returnsFalseIfInputIsNotLessThanOrEqualCompare_Numbers() {
        assertFalse(operator.evaluate(5, 3));
    }

    @Test
    void returnsTrueIfInputIsLessThanOrEqualCompare_Strings() {
        assertTrue(operator.evaluate("bat", "cat"));
        assertTrue(operator.evaluate("cat", "cat"));
    }

    @Test
    void returnsFalseIfInputIsNotLessThanOrEqualCompare_Strings() {
        assertFalse(operator.evaluate("cat", "bat"));
    }
} 