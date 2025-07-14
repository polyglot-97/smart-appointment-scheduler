package com.jatin.smart_appointment_scheduler.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientRequestDTO extends UserBaseDTO {
    @NotBlank(message = "MRN is mandatory")
    private String medicalRecordNumber;
}
