package com.jatin.smart_appointment_scheduler.dtos;

import com.jatin.smart_appointment_scheduler.enums.AppointmentStatus;
import com.jatin.smart_appointment_scheduler.enums.AppointmentType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDTO {
    private Long id;
    private Long patientId;
    private Long providerId;
    private Long clinicId;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;
    private AppointmentStatus status;
    private AppointmentType appointmentType;
    private String notes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 