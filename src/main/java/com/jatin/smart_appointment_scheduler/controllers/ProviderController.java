package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.ProviderRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ProviderResponseDTO;
import com.jatin.smart_appointment_scheduler.services.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;

    @PostMapping
    public ResponseEntity<ProviderResponseDTO> create(@RequestBody @Valid ProviderRequestDTO dto) {
        return ResponseEntity.ok(providerService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProviderResponseDTO>> getAll() {
        return ResponseEntity.ok(providerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(providerService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProviderRequestDTO dto) {
        return ResponseEntity.ok(providerService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        providerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
