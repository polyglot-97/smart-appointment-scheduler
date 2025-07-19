package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.dtos.PatientRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.PatientResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.Patient;
import com.jatin.smart_appointment_scheduler.enums.Role;
import com.jatin.smart_appointment_scheduler.repositories.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.Collections;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock private PatientRepository patientRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private PatientService patientService;

    // Mock dependencies here

    @Test
    void create_shouldSavePatient_whenValid() {
        PatientRequestDTO dto = Mockito.mock(PatientRequestDTO.class);
        Patient patient = Mockito.mock(Patient.class);
        Patient savedPatient = Mockito.mock(Patient.class);
        Mockito.when(dto.getEmail()).thenReturn("test@example.com");
        Mockito.when(dto.getPhone()).thenReturn("1234567890");
        Mockito.when(dto.getPassword()).thenReturn("password");
        Mockito.when(patientRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(patientRepository.findByPhone("1234567890")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encoded");
        Mockito.when(patientRepository.save(Mockito.any())).thenReturn(savedPatient);
        PatientResponseDTO response = patientService.create(dto);
        Mockito.verify(patientRepository).save(Mockito.any());
        assertNotNull(response);
    }

    @Test
    void create_shouldThrow_whenEmailExists() {
        PatientRequestDTO dto = Mockito.mock(PatientRequestDTO.class);
        Mockito.when(dto.getEmail()).thenReturn("test@example.com");
        Mockito.when(patientRepository.findByEmail("test@example.com")).thenReturn(Optional.of(Mockito.mock(Patient.class)));
        assertThrows(IllegalArgumentException.class, () -> patientService.create(dto));
    }

    @Test
    void create_shouldThrow_whenPhoneExists() {
        PatientRequestDTO dto = Mockito.mock(PatientRequestDTO.class);
        Mockito.when(dto.getEmail()).thenReturn("test@example.com");
        Mockito.when(dto.getPhone()).thenReturn("1234567890");
        Mockito.when(patientRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(patientRepository.findByPhone("1234567890")).thenReturn(Optional.of(Mockito.mock(Patient.class)));
        assertThrows(IllegalArgumentException.class, () -> patientService.create(dto));
    }

    @Test
    void getAll_shouldReturnList() {
        Mockito.when(patientRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(patientService.getAll());
    }

    @Test
    void getById_shouldReturnPatient_whenFound() {
        Patient patient = Mockito.mock(Patient.class);
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        assertNotNull(patientService.getById(1L));
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> patientService.getById(1L));
    }

    @Test
    void update_shouldUpdatePatient_whenFound() {
        Patient patient = Mockito.mock(Patient.class);
        PatientRequestDTO dto = Mockito.mock(PatientRequestDTO.class);
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("encoded");
        Mockito.when(patientRepository.save(Mockito.any())).thenReturn(patient);
        assertNotNull(patientService.update(1L, dto));
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        PatientRequestDTO dto = Mockito.mock(PatientRequestDTO.class);
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> patientService.update(1L, dto));
    }

    @Test
    void delete_shouldDelete_whenFound() {
        Mockito.when(patientRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> patientService.delete(1L));
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        Mockito.when(patientRepository.existsById(1L)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> patientService.delete(1L));
    }

    @Test
    void sampleTest() {
        // TODO: implement test
    }
} 