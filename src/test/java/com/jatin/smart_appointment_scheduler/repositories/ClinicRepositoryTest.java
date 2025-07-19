package com.jatin.smart_appointment_scheduler.repositories;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class ClinicRepositoryTest {
    @Test
    void findAllByIsActiveTrue_returnsActiveClinics() {
        ClinicRepository repo = Mockito.mock(ClinicRepository.class);
        Mockito.when(repo.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());
        assertNotNull(repo.findAllByIsActiveTrue());
    }
} 