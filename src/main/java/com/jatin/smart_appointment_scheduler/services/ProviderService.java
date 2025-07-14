package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.dtos.ProviderRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ProviderResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.Provider;
import com.jatin.smart_appointment_scheduler.enums.Role;
import com.jatin.smart_appointment_scheduler.repositories.ProviderRepository;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProviderService {
    private static final Logger logger = LoggerFactory.getLogger(ProviderService.class);
    private final ProviderRepository providerRepository;
    private final PasswordEncoder passwordEncoder;

    public ProviderResponseDTO create(ProviderRequestDTO dto) {
        try {
            if (providerRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new IllegalArgumentException("A provider with this email already exists.");
            }
            if (providerRepository.findByPhone(dto.getPhone()).isPresent()) {
                throw new IllegalArgumentException("A provider with this phone number already exists.");
            }
            Provider provider = Provider.builder()
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .gender(dto.getGender())
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .dob(dto.getDob())
                    .specilization(dto.getSpecilization())
                    .npiNumber(dto.getNpiNumber())
                    .role(Role.PROVIDER)
                    .isActive(true)
                    .build();
            provider.setPassword(passwordEncoder.encode(dto.getPassword()));
            return mapToDTO(providerRepository.save(provider));
        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in create provider: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in create provider", e);
            throw new RuntimeException("Failed to create provider", e);
        }
    }

    public List<ProviderResponseDTO> getAll() {
        try {
            return providerRepository.findAll().stream().map(this::mapToDTO).toList();
        } catch (Exception e) {
            logger.error("Unexpected error in getAll providers", e);
            throw new RuntimeException("Failed to fetch providers", e);
        }
    }

    public ProviderResponseDTO getById(Long id) {
        try {
            return providerRepository.findById(id).map(this::mapToDTO)
                    .orElseThrow(() -> new NoSuchElementException("Provider not found"));
        } catch (NoSuchElementException e) {
            logger.warn("Provider not found for id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in getById provider", e);
            throw new RuntimeException("Failed to fetch provider by id", e);
        }
    }

    public ProviderResponseDTO update(Long id, ProviderRequestDTO dto) {
        try {
            Provider existing = providerRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Provider not found"));

            existing.setFirstName(dto.getFirstName());
            existing.setLastName(dto.getLastName());
            existing.setGender(dto.getGender());
            existing.setEmail(dto.getEmail());
            existing.setPhone(dto.getPhone());
            existing.setDob(dto.getDob());
            existing.setSpecilization(dto.getSpecilization());
            existing.setNpiNumber(dto.getNpiNumber());
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));

            return mapToDTO(providerRepository.save(existing));
        } catch (NoSuchElementException e) {
            logger.warn("Provider not found for update id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in update provider", e);
            throw new RuntimeException("Failed to update provider", e);
        }
    }

    public void delete(Long id) {
        try {
            if (!providerRepository.existsById(id)) {
                throw new NoSuchElementException("Provider not found");
            }
            providerRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            logger.warn("Provider not found for delete id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in delete provider", e);
            throw new RuntimeException("Failed to delete provider", e);
        }
    }

    private ProviderResponseDTO mapToDTO(Provider p) {
        ProviderResponseDTO dto = new ProviderResponseDTO();
        dto.setId(p.getId()); dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName()); dto.setGender(p.getGender());
        dto.setEmail(p.getEmail()); dto.setPhone(p.getPhone());
        dto.setDob(p.getDob()); dto.setNpiNumber(p.getNpiNumber());
        dto.setSpecilization(p.getSpecilization()); dto.setRole(p.getRole());
        return dto;
    }
}