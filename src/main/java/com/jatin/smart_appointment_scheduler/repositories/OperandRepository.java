package com.jatin.smart_appointment_scheduler.repositories;

import com.jatin.smart_appointment_scheduler.entities.Operand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperandRepository extends JpaRepository<Operand, Long> {
    
    /**
     * Find operand by name
     */
    Optional<Operand> findByName(String name);
}
