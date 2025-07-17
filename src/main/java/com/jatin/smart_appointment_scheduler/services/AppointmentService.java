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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ProviderRepository providerRepository;
    private final ClinicRepository clinicRepository;

    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {
        try {
            Appointment appointment = mapToEntity(dto);
            appointment.setIsActive(true);
            return mapToDTO(appointmentRepository.save(appointment));
        } catch (Exception e) {
            logger.error("Unexpected error in create appointment", e);
            throw new RuntimeException("Failed to create appointment", e);
        }
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAll() {
        try {
            return appointmentRepository.findAllByIsActiveTrue().stream().map(this::mapToDTO).toList();
        } catch (Exception e) {
            logger.error("Unexpected error in getAll appointments", e);
            throw new RuntimeException("Failed to fetch appointments", e);
        }
    }

    @Transactional(readOnly = true)
    public AppointmentResponseDTO getById(Long id) {
        try {
            return appointmentRepository.findByIdAndIsActiveTrue(id).map(this::mapToDTO)
                    .orElseThrow(() -> new NoSuchElementException("Appointment not found or inactive"));
        } catch (NoSuchElementException e) {
            logger.warn("Appointment not found for id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in getById appointment", e);
            throw new RuntimeException("Failed to fetch appointment by id", e);
        }
    }

    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO dto) {
        try {
            Appointment existing = appointmentRepository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new NoSuchElementException("Appointment not found or inactive"));
            Appointment updated = mapToEntity(dto);
            existing.setPatient(updated.getPatient());
            existing.setProvider(updated.getProvider());
            existing.setClinic(updated.getClinic());
            existing.setAppointmentStart(updated.getAppointmentStart());
            existing.setAppointmentEnd(updated.getAppointmentEnd());
            existing.setStatus(updated.getStatus());
            existing.setAppointmentType(updated.getAppointmentType());
            existing.setNotes(updated.getNotes());
            return mapToDTO(appointmentRepository.save(existing));
        } catch (NoSuchElementException e) {
            logger.warn("Appointment not found for update id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in update appointment", e);
            throw new RuntimeException("Failed to update appointment", e);
        }
    }

    public void delete(Long id) {
        try {
            Appointment appointment = appointmentRepository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new NoSuchElementException("Appointment not found or already inactive"));
            appointment.setIsActive(false);
            appointmentRepository.save(appointment);
        } catch (NoSuchElementException e) {
            logger.warn("Appointment not found for delete id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in delete appointment", e);
            throw new RuntimeException("Failed to delete appointment", e);
        }
    }

    private Appointment mapToEntity(AppointmentRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("Provider not found"));
        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));
        return Appointment.builder()
                .patient(patient)
                .provider(provider)
                .clinic(clinic)
                .appointmentStart(dto.getAppointmentStart())
                .appointmentEnd(dto.getAppointmentEnd())
                .status(dto.getStatus())
                .appointmentType(dto.getAppointmentType())
                .notes(dto.getNotes())
                .isActive(true)
                .build();
    }

    private AppointmentResponseDTO mapToDTO(Appointment entity) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(entity.getId());
        dto.setPatientId(entity.getPatient().getId());
        dto.setProviderId(entity.getProvider().getId());
        dto.setClinicId(entity.getClinic().getId());
        dto.setAppointmentStart(entity.getAppointmentStart());
        dto.setAppointmentEnd(entity.getAppointmentEnd());
        dto.setStatus(entity.getStatus());
        dto.setAppointmentType(entity.getAppointmentType());
        dto.setNotes(entity.getNotes()); // This should trigger the LOB load
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
} 