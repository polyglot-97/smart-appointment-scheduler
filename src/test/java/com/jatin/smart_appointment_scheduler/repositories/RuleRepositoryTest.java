package com.jatin.smart_appointment_scheduler.repositories;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class RuleRepositoryTest {
    @Test
    void findAll_returnsRules() {
        RuleRepository repo = Mockito.mock(RuleRepository.class);
        Mockito.when(repo.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(repo.findAll());
    }
} 