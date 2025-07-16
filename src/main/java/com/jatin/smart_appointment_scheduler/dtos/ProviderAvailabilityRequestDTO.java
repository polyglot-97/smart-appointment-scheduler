package com.jatin.smart_appointment_scheduler.dtos;

import com.jatin.smart_appointment_scheduler.enums.WeekDays;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class ProviderAvailabilityRequestDTO {
    @NotNull(message = "Provider ID is mandatory")
    private Long providerId;
    @NotNull(message = "Clinic ID is mandatory")
    private Long clinicId;
    @NotNull(message = "Days are mandatory")
    private Set<WeekDays> days;
    @NotNull(message = "Start time is mandatory")
    private LocalTime startTime;
    @NotNull(message = "End time is mandatory")
    private LocalTime endTime;
} 