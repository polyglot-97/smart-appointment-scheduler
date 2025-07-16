package com.jatin.smart_appointment_scheduler.dtos;

import com.jatin.smart_appointment_scheduler.enums.WeekDays;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class ProviderAvailabilityResponseDTO {
    private Long id;
    private Long providerId;
    private Long clinicId;
    private Set<WeekDays> days;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isActive;
} 