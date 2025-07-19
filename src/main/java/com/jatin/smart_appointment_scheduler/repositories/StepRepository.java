package com.jatin.smart_appointment_scheduler.repositories;

import com.jatin.smart_appointment_scheduler.entities.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    
    /**
     * Find steps by rule ID
     */
    List<Step> findByRuleId(Long ruleId);
    
    /**
     * Find step by name
     */
    Step findByName(String name);
}
