package com.jatin.smart_appointment_scheduler.repositories;

import com.jatin.smart_appointment_scheduler.entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
    
    /**
     * Find operator by name
     */
    Optional<Operator> findByName(String name);
}
