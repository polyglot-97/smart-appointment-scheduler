package com.jatin.smart_appointment_scheduler.services.operators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EqualsOperatorTest {
    private final EqualsOperator equalsOperator = new EqualsOperator();

    @Test
    void evaluate_returnsTrue_whenBothNull() {
        assertTrue(equalsOperator.evaluate(null, null));
    }

    @Test
    void evaluate_returnsFalse_whenOneNull() {
        assertFalse(equalsOperator.evaluate(null, "abc"));
        assertFalse(equalsOperator.evaluate("abc", null));
    }

    @Test
    void evaluate_returnsTrue_whenEqualStrings() {
        assertTrue(equalsOperator.evaluate("abc", "abc"));
    }

    @Test
    void evaluate_returnsFalse_whenNotEqualStrings() {
        assertFalse(equalsOperator.evaluate("abc", "def"));
    }

    @Test
    void evaluate_returnsTrue_whenDifferentTypesButEqualString() {
        assertTrue(equalsOperator.evaluate(123, "123"));
    }
} 