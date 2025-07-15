package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.dtos.ClinicRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ClinicResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.Clinic;
import com.jatin.smart_appointment_scheduler.repositories.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private static final Logger logger = LoggerFactory.getLogger(ClinicService.class);
    private final ClinicRepository clinicRepository;

    public ClinicResponseDTO create(ClinicRequestDTO dto) {
        try {
            Clinic clinic = Clinic.builder()
                    .name(dto.getName())
                    .address(dto.getAddress())
                    .isActive(true)
                    .build();
            return mapToDTO(clinicRepository.save(clinic));
        } catch (Exception e) {
            logger.error("Unexpected error in create clinic", e);
            throw new RuntimeException("Failed to create clinic", e);
        }
    }

    public List<ClinicResponseDTO> getAll() {
        try {
            return clinicRepository.findAllByIsActiveTrue().stream().map(this::mapToDTO).toList();
        } catch (Exception e) {
            logger.error("Unexpected error in getAll clinics", e);
            throw new RuntimeException("Failed to fetch clinics", e);
        }
    }

    public ClinicResponseDTO getById(Long id) {
        try {
            return clinicRepository.findByIdAndIsActiveTrue(id).map(this::mapToDTO)
                    .orElseThrow(() -> new NoSuchElementException("Clinic not found or inactive"));
        } catch (NoSuchElementException e) {
            logger.warn("Clinic not found for id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in getById clinic", e);
            throw new RuntimeException("Failed to fetch clinic by id", e);
        }
    }

    public ClinicResponseDTO update(Long id, ClinicRequestDTO dto) {
        try {
            Clinic existing = clinicRepository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new NoSuchElementException("Clinic not found or inactive"));
            existing.setName(dto.getName());
            existing.setAddress(dto.getAddress());
            return mapToDTO(clinicRepository.save(existing));
        } catch (NoSuchElementException e) {
            logger.warn("Clinic not found for update id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in update clinic", e);
            throw new RuntimeException("Failed to update clinic", e);
        }
    }

    public void delete(Long id) {
        try {
            Clinic clinic = clinicRepository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new NoSuchElementException("Clinic not found or already inactive"));
            clinic.setIsActive(false);
            clinicRepository.save(clinic);
        } catch (NoSuchElementException e) {
            logger.warn("Clinic not found for delete id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in delete clinic", e);
            throw new RuntimeException("Failed to delete clinic", e);
        }
    }

    private ClinicResponseDTO mapToDTO(Clinic c) {
        ClinicResponseDTO dto = new ClinicResponseDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setAddress(c.getAddress());
        return dto;
    }
} 