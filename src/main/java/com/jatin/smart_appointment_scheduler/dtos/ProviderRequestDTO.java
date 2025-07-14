package com.jatin.smart_appointment_scheduler.dtos;

import com.jatin.smart_appointment_scheduler.enums.Specialization;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProviderRequestDTO extends UserBaseDTO {
    @NotBlank(message = "Specilization is mandatory")
    private Specialization specilization;
    @NotBlank(message = "NPI is mandatory")
    private String npiNumber;
}