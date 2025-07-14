package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.dtos.PatientRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.PatientResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.Patient;
import com.jatin.smart_appointment_scheduler.enums.Role;
import com.jatin.smart_appointment_scheduler.repositories.PatientRepository;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PatientService {
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientResponseDTO create(PatientRequestDTO dto) {
        try {
            if (patientRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new IllegalArgumentException("A patient with this email already exists.");
            }
            if (patientRepository.findByPhone(dto.getPhone()).isPresent()) {
                throw new IllegalArgumentException("A patient with this phone number already exists.");
            }
            Patient patient = Patient.builder()
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .gender(dto.getGender())
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .dob(dto.getDob())
                    .medicalRecordNumber(dto.getMedicalRecordNumber())
                    .role(Role.PATIENT)
                    .isActive(true)
                    .build();
            patient.setPassword(passwordEncoder.encode(dto.getPassword()));
            return mapToDTO(patientRepository.save(patient));
        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in create patient: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in create patient", e);
            throw new RuntimeException("Failed to create patient", e);
        }
    }

    public List<PatientResponseDTO> getAll() {
        try {
            return patientRepository.findAll().stream().map(this::mapToDTO).toList();
        } catch (Exception e) {
            logger.error("Unexpected error in getAll patients", e);
            throw new RuntimeException("Failed to fetch patients", e);
        }
    }

    public PatientResponseDTO getById(Long id) {
        try {
            return patientRepository.findById(id).map(this::mapToDTO)
                    .orElseThrow(() -> new NoSuchElementException("Patient not found"));
        } catch (NoSuchElementException e) {
            logger.warn("Patient not found for id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in getById patient", e);
            throw new RuntimeException("Failed to fetch patient by id", e);
        }
    }

    public PatientResponseDTO update(Long id, PatientRequestDTO dto) {
        try {
            Patient existing = patientRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Patient not found"));

            existing.setFirstName(dto.getFirstName());
            existing.setLastName(dto.getLastName());
            existing.setGender(dto.getGender());
            existing.setEmail(dto.getEmail());
            existing.setPhone(dto.getPhone());
            existing.setDob(dto.getDob());
            existing.setMedicalRecordNumber(dto.getMedicalRecordNumber());
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));

            return mapToDTO(patientRepository.save(existing));
        } catch (NoSuchElementException e) {
            logger.warn("Patient not found for update id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in update patient", e);
            throw new RuntimeException("Failed to update patient", e);
        }
    }

    public void delete(Long id) {
        try {
            if (!patientRepository.existsById(id)) {
                throw new NoSuchElementException("Patient not found");
            }
            patientRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            logger.warn("Patient not found for delete id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in delete patient", e);
            throw new RuntimeException("Failed to delete patient", e);
        }
    }

    private PatientResponseDTO mapToDTO(Patient p) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(p.getId()); dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName()); dto.setGender(p.getGender());
        dto.setEmail(p.getEmail()); dto.setPhone(p.getPhone());
        dto.setDob(p.getDob()); dto.setMedicalRecordNumber(p.getMedicalRecordNumber());
        dto.setRole(p.getRole());
        return dto;
    }
}
