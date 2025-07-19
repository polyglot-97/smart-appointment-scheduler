package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.entities.ProviderAvailability;
import com.jatin.smart_appointment_scheduler.repositories.ProviderAvailabilityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import java.util.Optional;
import java.util.Collections;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProviderAvailabilityServiceTest {
    @Mock private ProviderAvailabilityRepository repository;
    @InjectMocks private ProviderAvailabilityService providerAvailabilityService;

    // Mock dependencies here

    @Test
    void create_shouldSaveProviderAvailability_whenValid() {
        ProviderAvailability availability = Mockito.mock(ProviderAvailability.class);
        ProviderAvailability saved = Mockito.mock(ProviderAvailability.class);
        Mockito.when(repository.save(Mockito.any())).thenReturn(saved);
        ProviderAvailability response = providerAvailabilityService.create(availability);
        Mockito.verify(repository).save(Mockito.any());
        assertNotNull(response);
    }

    @Test
    void getAll_shouldReturnList() {
        Mockito.when(repository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());
        assertNotNull(providerAvailabilityService.getAll());
    }

    @Test
    void getById_shouldReturnProviderAvailability_whenFound() {
        ProviderAvailability availability = Mockito.mock(ProviderAvailability.class);
        Mockito.when(repository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(availability));
        assertNotNull(providerAvailabilityService.getById(1L));
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        Mockito.when(repository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> providerAvailabilityService.getById(1L));
    }

    @Test
    void update_shouldUpdateProviderAvailability_whenFound() {
        ProviderAvailability availability = Mockito.mock(ProviderAvailability.class);
        ProviderAvailability updated = Mockito.mock(ProviderAvailability.class);
        Mockito.when(repository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(availability));
        Mockito.when(repository.save(Mockito.any())).thenReturn(updated);
        assertNotNull(providerAvailabilityService.update(1L, updated));
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        ProviderAvailability updated = Mockito.mock(ProviderAvailability.class);
        Mockito.when(repository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> providerAvailabilityService.update(1L, updated));
    }

    @Test
    void delete_shouldSetInactive_whenFound() {
        ProviderAvailability availability = Mockito.mock(ProviderAvailability.class);
        Mockito.when(repository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(availability));
        Mockito.when(repository.save(Mockito.any())).thenReturn(availability);
        assertDoesNotThrow(() -> providerAvailabilityService.delete(1L));
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        Mockito.when(repository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> providerAvailabilityService.delete(1L));
    }

    @Test
    void sampleTest() {
        // TODO: implement test
    }
} 