package com.jatin.smart_appointment_scheduler.repositories;

import com.jatin.smart_appointment_scheduler.entities.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    
    /**
     * Find all active rules
     */
    List<Rule> findByIsActiveTrue();
    
    /**
     * Find rule by name
     */
    Rule findByNameAndIsActiveTrue(String name);
}
