package com.jatin.smart_appointment_scheduler.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EncounterRequestDTO {
    @NotNull(message = "Appointment ID is mandatory")
    private Long appointmentId;
    private List<String> cptCodes;
    private List<String> icdCodes;
    private List<String> modifiers;
    private List<Integer> units;
} 