package com.jatin.smart_appointment_scheduler.services.operators;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ContainsOperatorTest {
    private final ContainsOperator operator = new ContainsOperator();

    @Test
    void returnsFalseIfInputOrCompareIsNull() {
        assertFalse(operator.evaluate(null, "a"));
        assertFalse(operator.evaluate("a", null));
    }

    @Test
    void returnsTrueIfListContainsValue() {
        List<String> list = Arrays.asList("a", "b", "c");
        assertTrue(operator.evaluate(list, "b"));
    }

    @Test
    void returnsFalseIfListDoesNotContainValue() {
        List<String> list = Arrays.asList("a", "b", "c");
        assertFalse(operator.evaluate(list, "z"));
    }

    @Test
    void returnsTrueIfStringContainsSubstring() {
        assertTrue(operator.evaluate("hello world", "world"));
    }

    @Test
    void returnsFalseIfStringDoesNotContainSubstring() {
        assertFalse(operator.evaluate("hello world", "mars"));
    }
} 