package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.dtos.ClinicRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ClinicResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.Clinic;
import com.jatin.smart_appointment_scheduler.repositories.ClinicRepository;
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
class ClinicServiceTest {
    @Mock private ClinicRepository clinicRepository;
    @InjectMocks private ClinicService clinicService;

    // Mock dependencies here

    @Test
    void create_shouldSaveClinic_whenValid() {
        ClinicRequestDTO dto = Mockito.mock(ClinicRequestDTO.class);
        Clinic clinic = Mockito.mock(Clinic.class);
        Clinic savedClinic = Mockito.mock(Clinic.class);
        Mockito.when(dto.getName()).thenReturn("Test Clinic");
        Mockito.when(dto.getAddress()).thenReturn("123 Main St");
        Mockito.when(clinicRepository.save(Mockito.any())).thenReturn(savedClinic);
        ClinicResponseDTO response = clinicService.create(dto);
        Mockito.verify(clinicRepository).save(Mockito.any());
        assertNotNull(response);
    }

    @Test
    void getAll_shouldReturnList() {
        Mockito.when(clinicRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());
        assertNotNull(clinicService.getAll());
    }

    @Test
    void getById_shouldReturnClinic_whenFound() {
        Clinic clinic = Mockito.mock(Clinic.class);
        Mockito.when(clinicRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(clinic));
        assertNotNull(clinicService.getById(1L));
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        Mockito.when(clinicRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> clinicService.getById(1L));
    }

    @Test
    void update_shouldUpdateClinic_whenFound() {
        Clinic clinic = Mockito.mock(Clinic.class);
        ClinicRequestDTO dto = Mockito.mock(ClinicRequestDTO.class);
        Mockito.when(clinicRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(clinic));
        Mockito.when(clinicRepository.save(Mockito.any())).thenReturn(clinic);
        assertNotNull(clinicService.update(1L, dto));
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        ClinicRequestDTO dto = Mockito.mock(ClinicRequestDTO.class);
        Mockito.when(clinicRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> clinicService.update(1L, dto));
    }

    @Test
    void delete_shouldSetInactive_whenFound() {
        Clinic clinic = Mockito.mock(Clinic.class);
        Mockito.when(clinicRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(clinic));
        Mockito.when(clinicRepository.save(Mockito.any())).thenReturn(clinic);
        assertDoesNotThrow(() -> clinicService.delete(1L));
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        Mockito.when(clinicRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> clinicService.delete(1L));
    }

    @Test
    void sampleTest() {
        // TODO: implement test
    }
} 