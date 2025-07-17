package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.dtos.EncounterRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.EncounterResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.Appointment;
import com.jatin.smart_appointment_scheduler.entities.Encounter;
import com.jatin.smart_appointment_scheduler.entities.Patient;
import com.jatin.smart_appointment_scheduler.entities.Provider;
import com.jatin.smart_appointment_scheduler.repositories.AppointmentRepository;
import com.jatin.smart_appointment_scheduler.repositories.EncounterRepository;
import com.jatin.smart_appointment_scheduler.repositories.PatientRepository;
import com.jatin.smart_appointment_scheduler.repositories.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EncounterService {
    private static final Logger logger = LoggerFactory.getLogger(EncounterService.class);
    private final EncounterRepository encounterRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ProviderRepository providerRepository;

    public EncounterResponseDTO create(EncounterRequestDTO dto) {
        try {
            Encounter encounter = mapToEntity(dto);
            return mapToDTO(encounterRepository.save(encounter));
        } catch (Exception e) {
            logger.error("Unexpected error in create encounter", e);
            throw new RuntimeException("Failed to create encounter", e);
        }
    }

    @Transactional(readOnly = true)
    public List<EncounterResponseDTO> getAll() {
        try {
            return encounterRepository.findAllByIsActiveTrue().stream().map(this::mapToDTO).toList();
        } catch (Exception e) {
            logger.error("Unexpected error in getAll encounters", e);
            throw new RuntimeException("Failed to fetch encounters", e);
        }
    }

    @Transactional(readOnly = true)
    public EncounterResponseDTO getById(Long id) {
        try {
            return encounterRepository.findByIdAndIsActiveTrue(id).map(this::mapToDTO)
                    .orElseThrow(() -> new NoSuchElementException("Encounter not found or inactive"));
        } catch (NoSuchElementException e) {
            logger.warn("Encounter not found for id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in getById encounter", e);
            throw new RuntimeException("Failed to fetch encounter by id", e);
        }
    }

    public EncounterResponseDTO update(Long id, EncounterRequestDTO dto) {
        try {
            Encounter existing = encounterRepository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new NoSuchElementException("Encounter not found or inactive"));
            Encounter updated = mapToEntity(dto);
            existing.setAppointment(updated.getAppointment());
            existing.setCptCodes(updated.getCptCodes());
            existing.setIcdCodes(updated.getIcdCodes());
            existing.setModifiers(updated.getModifiers());
            existing.setUnits(updated.getUnits());
            // serviceDate and isActive are not updated here
            return mapToDTO(encounterRepository.save(existing));
        } catch (NoSuchElementException e) {
            logger.warn("Encounter not found for update id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in update encounter", e);
            throw new RuntimeException("Failed to update encounter", e);
        }
    }

    public void delete(Long id) {
        try {
            Encounter encounter = encounterRepository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new NoSuchElementException("Encounter not found or already inactive"));
            encounter.setIsActive(false);
            encounterRepository.save(encounter);
        } catch (NoSuchElementException e) {
            logger.warn("Encounter not found for delete id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in delete encounter", e);
            throw new RuntimeException("Failed to delete encounter", e);
        }
    }

    private Encounter mapToEntity(EncounterRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        return Encounter.builder()
                .appointment(appointment)
                .cptCodes(dto.getCptCodes())
                .icdCodes(dto.getIcdCodes())
                .modifiers(dto.getModifiers())
                .units(dto.getUnits())
                .serviceDate(LocalDateTime.now(ZoneOffset.UTC))
                .isActive(true)
                .build();
    }

    private EncounterResponseDTO mapToDTO(Encounter entity) {
        EncounterResponseDTO dto = new EncounterResponseDTO();
        dto.setId(entity.getId());
        dto.setAppointmentId(entity.getAppointment().getId());
        dto.setCptCodes(entity.getCptCodes());
        dto.setIcdCodes(entity.getIcdCodes());
        dto.setModifiers(entity.getModifiers());
        dto.setUnits(entity.getUnits());
        dto.setServiceDate(entity.getServiceDate());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }
} 