package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.ProviderAvailabilityRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ProviderAvailabilityResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.ProviderAvailability;
import com.jatin.smart_appointment_scheduler.entities.Provider;
import com.jatin.smart_appointment_scheduler.entities.Clinic;
import com.jatin.smart_appointment_scheduler.services.ProviderAvailabilityService;
import com.jatin.smart_appointment_scheduler.repositories.ProviderRepository;
import com.jatin.smart_appointment_scheduler.repositories.ClinicRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/provider-availability")
@RequiredArgsConstructor
public class ProviderAvailabilityController {
    private final ProviderAvailabilityService service;
    private final ProviderRepository providerRepository;
    private final ClinicRepository clinicRepository;

    @PostMapping
    public ResponseEntity<ProviderAvailabilityResponseDTO> create(@RequestBody @Valid ProviderAvailabilityRequestDTO dto) {
        ProviderAvailability availability = mapToEntity(dto);
        return ResponseEntity.ok(mapToDTO(service.create(availability)));
    }

    @GetMapping
    public ResponseEntity<List<ProviderAvailabilityResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderAvailabilityResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapToDTO(service.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderAvailabilityResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProviderAvailabilityRequestDTO dto) {
        ProviderAvailability updated = mapToEntity(dto);
        return ResponseEntity.ok(mapToDTO(service.update(id, updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ProviderAvailability mapToEntity(ProviderAvailabilityRequestDTO dto) {
        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("Provider not found"));
        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));
        return ProviderAvailability.builder()
                .provider(provider)
                .clinic(clinic)
                .days(dto.getDays())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .isActive(true)
                .build();
    }

    private ProviderAvailabilityResponseDTO mapToDTO(ProviderAvailability entity) {
        ProviderAvailabilityResponseDTO dto = new ProviderAvailabilityResponseDTO();
        dto.setId(entity.getId());
        dto.setProviderId(entity.getProvider().getId());
        dto.setClinicId(entity.getClinic().getId());
        dto.setDays(entity.getDays());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }
} 