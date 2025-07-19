package com.jatin.smart_appointment_scheduler.services.operators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotEqualsOperatorTest {
    private final NotEqualsOperator operator = new NotEqualsOperator();

    @Test
    void returnsFalseIfBothNull() {
        assertFalse(operator.evaluate(null, null));
    }

    @Test
    void returnsTrueIfOneIsNull() {
        assertTrue(operator.evaluate(null, "abc"));
        assertTrue(operator.evaluate("abc", null));
    }

    @Test
    void returnsFalseIfEqualStrings() {
        assertFalse(operator.evaluate("abc", "abc"));
    }

    @Test
    void returnsTrueIfNotEqualStrings() {
        assertTrue(operator.evaluate("abc", "def"));
    }

    @Test
    void returnsFalseIfEqualDifferentTypes() {
        assertFalse(operator.evaluate(123, "123"));
    }
} 