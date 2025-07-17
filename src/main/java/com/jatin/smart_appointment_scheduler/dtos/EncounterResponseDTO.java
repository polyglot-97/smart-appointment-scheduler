package com.jatin.smart_appointment_scheduler.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EncounterResponseDTO {
    private Long id;
    private Long appointmentId;
    private List<String> cptCodes;
    private List<String> icdCodes;
    private List<String> modifiers;
    private List<Integer> units;
    private LocalDateTime serviceDate;
    private Boolean isActive;
} 