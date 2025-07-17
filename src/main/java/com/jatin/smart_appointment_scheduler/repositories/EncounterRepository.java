package com.jatin.smart_appointment_scheduler.repositories;

import com.jatin.smart_appointment_scheduler.entities.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {
    List<Encounter> findAllByIsActiveTrue();
    Optional<Encounter> findByIdAndIsActiveTrue(Long id);
} 