package com.jatin.smart_appointment_scheduler.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ClinicRequestDTO {
    @NotBlank(message = "Clinic name is mandatory")
    private String name;
    @NotBlank(message = "Clinic address is mandatory")
    private String address;
} 