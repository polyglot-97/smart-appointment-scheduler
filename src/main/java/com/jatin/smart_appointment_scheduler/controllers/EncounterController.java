package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.EncounterRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.EncounterResponseDTO;
import com.jatin.smart_appointment_scheduler.services.EncounterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encounter")
@RequiredArgsConstructor
public class EncounterController {
    private final EncounterService encounterService;

    @PostMapping
    public ResponseEntity<EncounterResponseDTO> create(@RequestBody @Valid EncounterRequestDTO dto) {
        return ResponseEntity.ok(encounterService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<EncounterResponseDTO>> getAll() {
        return ResponseEntity.ok(encounterService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncounterResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(encounterService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EncounterResponseDTO> update(@PathVariable Long id, @RequestBody @Valid EncounterRequestDTO dto) {
        return ResponseEntity.ok(encounterService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        encounterService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 