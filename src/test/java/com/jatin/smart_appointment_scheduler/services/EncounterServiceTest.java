package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.dtos.EncounterRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.EncounterResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.Encounter;
import com.jatin.smart_appointment_scheduler.entities.Appointment;
import com.jatin.smart_appointment_scheduler.repositories.EncounterRepository;
import com.jatin.smart_appointment_scheduler.repositories.AppointmentRepository;
import org.mockito.Mockito;
import java.util.Optional;
import java.util.Collections;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EncounterServiceTest {
    @Mock private EncounterRepository encounterRepository;
    @Mock private AppointmentRepository appointmentRepository;
    @InjectMocks private EncounterService encounterService;

    // Mock dependencies here

    @Test
    void create_shouldSaveEncounter_whenValid() {
        EncounterRequestDTO dto = Mockito.mock(EncounterRequestDTO.class);
        Appointment appointment = Mockito.mock(Appointment.class);
        Encounter encounter = Mockito.mock(Encounter.class);
        Encounter savedEncounter = Mockito.mock(Encounter.class);
        Mockito.when(dto.getAppointmentId()).thenReturn(1L);
        Mockito.when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        Mockito.when(encounterRepository.save(Mockito.any())).thenReturn(savedEncounter);
        EncounterResponseDTO response = encounterService.create(dto);
        Mockito.verify(encounterRepository).save(Mockito.any());
        assertNotNull(response);
    }

    @Test
    void getAll_shouldReturnList() {
        Mockito.when(encounterRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());
        assertNotNull(encounterService.getAll());
    }

    @Test
    void getById_shouldReturnEncounter_whenFound() {
        Encounter encounter = Mockito.mock(Encounter.class);
        Mockito.when(encounterRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(encounter));
        assertNotNull(encounterService.getById(1L));
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        Mockito.when(encounterRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> encounterService.getById(1L));
    }

    @Test
    void update_shouldUpdateEncounter_whenFound() {
        Encounter encounter = Mockito.mock(Encounter.class);
        EncounterRequestDTO dto = Mockito.mock(EncounterRequestDTO.class);
        Mockito.when(encounterRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(encounter));
        Mockito.when(encounterRepository.save(Mockito.any())).thenReturn(encounter);
        assertNotNull(encounterService.update(1L, dto));
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        EncounterRequestDTO dto = Mockito.mock(EncounterRequestDTO.class);
        Mockito.when(encounterRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> encounterService.update(1L, dto));
    }

    @Test
    void delete_shouldSetInactive_whenFound() {
        Encounter encounter = Mockito.mock(Encounter.class);
        Mockito.when(encounterRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(encounter));
        Mockito.when(encounterRepository.save(Mockito.any())).thenReturn(encounter);
        assertDoesNotThrow(() -> encounterService.delete(1L));
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        Mockito.when(encounterRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> encounterService.delete(1L));
    }

    @Test
    void sampleTest() {
        // TODO: implement test
    }
} 