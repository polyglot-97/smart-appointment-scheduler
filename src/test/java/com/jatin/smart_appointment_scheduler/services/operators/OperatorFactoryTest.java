package com.jatin.smart_appointment_scheduler.services.operators;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class OperatorFactoryTest {
    @Test
    void returnsOperatorByName() {
        EqualsOperator equals = new EqualsOperator();
        OperatorFactory factory = new OperatorFactory(Arrays.asList(equals));
        assertSame(equals, factory.getOperator("EQUALS"));
    }

    @Test
    void throwsIfOperatorNotFound() {
        OperatorFactory factory = new OperatorFactory(Arrays.asList(new EqualsOperator()));
        assertThrows(IllegalArgumentException.class, () -> factory.getOperator("NOT_FOUND"));
    }

    @Test
    void returnsAllAvailableOperators() {
        EqualsOperator equals = new EqualsOperator();
        InOperator in = new InOperator();
        OperatorFactory factory = new OperatorFactory(Arrays.asList(equals, in));
        Set<String> expected = new HashSet<>(Arrays.asList("EQUALS", "IN"));
        assertEquals(expected, factory.getAvailableOperators());
    }
} 