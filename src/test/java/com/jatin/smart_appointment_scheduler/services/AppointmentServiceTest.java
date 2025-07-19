package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.dtos.AppointmentRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.AppointmentResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.Appointment;
import com.jatin.smart_appointment_scheduler.entities.Patient;
import com.jatin.smart_appointment_scheduler.entities.Provider;
import com.jatin.smart_appointment_scheduler.entities.Clinic;
import com.jatin.smart_appointment_scheduler.repositories.AppointmentRepository;
import com.jatin.smart_appointment_scheduler.repositories.PatientRepository;
import com.jatin.smart_appointment_scheduler.repositories.ProviderRepository;
import com.jatin.smart_appointment_scheduler.repositories.ClinicRepository;
import com.jatin.smart_appointment_scheduler.services.RulesEngineService;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.Collections;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
    @Mock private AppointmentRepository appointmentRepository;
    @Mock private PatientRepository patientRepository;
    @Mock private ProviderRepository providerRepository;
    @Mock private ClinicRepository clinicRepository;
    @Mock private RulesEngineService rulesEngineService;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void create_shouldSaveAppointment_whenAllRulesPass() {
        AppointmentRequestDTO dto = Mockito.mock(AppointmentRequestDTO.class);
        Patient patient = Mockito.mock(Patient.class);
        Provider provider = Mockito.mock(Provider.class);
        Clinic clinic = Mockito.mock(Clinic.class);
        Appointment appointment = Mockito.mock(Appointment.class);
        Appointment savedAppointment = Mockito.mock(Appointment.class);
        Mockito.when(dto.getPatientId()).thenReturn(1L);
        Mockito.when(dto.getProviderId()).thenReturn(2L);
        Mockito.when(dto.getClinicId()).thenReturn(3L);
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        Mockito.when(providerRepository.findById(2L)).thenReturn(Optional.of(provider));
        Mockito.when(clinicRepository.findById(3L)).thenReturn(Optional.of(clinic));
        Mockito.when(rulesEngineService.evaluateRulesForAppointment(Mockito.any())).thenReturn(Collections.emptyMap());
        Mockito.when(appointmentRepository.save(Mockito.any())).thenReturn(savedAppointment);
        Mockito.when(savedAppointment.getNotes()).thenReturn(null);
        AppointmentResponseDTO response = appointmentService.create(dto);
        Mockito.verify(appointmentRepository).save(Mockito.any());
        assertNotNull(response);
    }

    @Test
    void create_shouldThrow_whenRuleBlocksCreation() {
        AppointmentRequestDTO dto = Mockito.mock(AppointmentRequestDTO.class);
        Patient patient = Mockito.mock(Patient.class);
        Provider provider = Mockito.mock(Provider.class);
        Clinic clinic = Mockito.mock(Clinic.class);
        Mockito.when(dto.getPatientId()).thenReturn(1L);
        Mockito.when(dto.getProviderId()).thenReturn(2L);
        Mockito.when(dto.getClinicId()).thenReturn(3L);
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        Mockito.when(providerRepository.findById(2L)).thenReturn(Optional.of(provider));
        Mockito.when(clinicRepository.findById(3L)).thenReturn(Optional.of(clinic));
        RulesEngineService.RuleEvaluationResult failResult = Mockito.mock(RulesEngineService.RuleEvaluationResult.class);
        Mockito.when(failResult.isPassed()).thenReturn(false);
        Mockito.when(failResult.getFailAction()).thenReturn("BLOCK");
        Mockito.when(failResult.getFailedStep()).thenReturn("Some step");
        Mockito.when(rulesEngineService.evaluateRulesForAppointment(Mockito.any())).thenReturn(Map.of("rule1", failResult));
        assertThrows(IllegalStateException.class, () -> appointmentService.create(dto));
    }

    @Test
    void create_shouldThrow_whenPatientNotFound() {
        AppointmentRequestDTO dto = Mockito.mock(AppointmentRequestDTO.class);
        Mockito.when(dto.getPatientId()).thenReturn(1L);
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> appointmentService.create(dto));
    }

    @Test
    void sampleTest() {
        // TODO: implement test
    }
} 