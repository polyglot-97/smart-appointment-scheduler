package com.jatin.smart_appointment_scheduler.dtos;

import com.jatin.smart_appointment_scheduler.enums.Gender;
import com.jatin.smart_appointment_scheduler.enums.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDTO {
    private Long id;
    private String firstName, lastName;
    private Gender gender;
    private String email, phone;
    private LocalDate dob;
    private Role role;
    private String medicalRecordNumber;
}
