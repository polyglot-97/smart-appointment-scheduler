package com.jatin.smart_appointment_scheduler.services.operators;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InOperatorTest {
    private final InOperator operator = new InOperator();

    @Test
    void returnsFalseIfInputOrCompareIsNull() {
        assertFalse(operator.evaluate(null, Arrays.asList("a")));
        assertFalse(operator.evaluate("a", null));
    }

    @Test
    void returnsTrueIfInputIsInList() {
        List<String> list = Arrays.asList("a", "b", "c");
        assertTrue(operator.evaluate("b", list));
    }

    @Test
    void returnsFalseIfInputIsNotInList() {
        List<String> list = Arrays.asList("a", "b", "c");
        assertFalse(operator.evaluate("z", list));
    }

    @Test
    void returnsTrueIfInputIsInCsvString() {
        assertTrue(operator.evaluate("b", "a,b,c"));
    }

    @Test
    void returnsFalseIfInputIsNotInCsvString() {
        assertFalse(operator.evaluate("z", "a,b,c"));
    }
} 