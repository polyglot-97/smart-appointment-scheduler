package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.ClinicRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ClinicResponseDTO;
import com.jatin.smart_appointment_scheduler.services.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clinic")
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;

    @PostMapping
    public ResponseEntity<ClinicResponseDTO> create(@RequestBody @Valid ClinicRequestDTO dto) {
        return ResponseEntity.ok(clinicService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClinicResponseDTO>> getAll() {
        return ResponseEntity.ok(clinicService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clinicService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ClinicRequestDTO dto) {
        return ResponseEntity.ok(clinicService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clinicService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 