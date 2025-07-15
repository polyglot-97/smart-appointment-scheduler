package com.jatin.smart_appointment_scheduler.repositories;

import com.jatin.smart_appointment_scheduler.entities.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findAllByIsActiveTrue();
    Optional<Clinic> findByIdAndIsActiveTrue(Long id);
} 