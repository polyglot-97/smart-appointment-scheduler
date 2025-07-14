package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.PatientRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.PatientResponseDTO;
import com.jatin.smart_appointment_scheduler.services.PatientService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDTO> create(@Valid @RequestBody PatientRequestDTO dto) {
        return ResponseEntity.ok(patientService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAll() {
        return ResponseEntity.ok(patientService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> update(@PathVariable Long id, @RequestBody @Valid PatientRequestDTO dto) {
        return ResponseEntity.ok(patientService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}