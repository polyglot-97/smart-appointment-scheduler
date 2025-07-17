package com.jatin.smart_appointment_scheduler.dtos;

import com.jatin.smart_appointment_scheduler.enums.AppointmentStatus;
import com.jatin.smart_appointment_scheduler.enums.AppointmentType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {
    @NotNull(message = "Patient ID is mandatory")
    private Long patientId;
    @NotNull(message = "Provider ID is mandatory")
    private Long providerId;
    @NotNull(message = "Clinic ID is mandatory")
    private Long clinicId;
    @NotNull(message = "Appointment start is mandatory")
    private LocalDateTime appointmentStart;
    @NotNull(message = "Appointment end is mandatory")
    private LocalDateTime appointmentEnd;
    @NotNull(message = "Status is mandatory")
    private AppointmentStatus status;
    @NotNull(message = "Appointment type is mandatory")
    private AppointmentType appointmentType;
    private String notes;
} 