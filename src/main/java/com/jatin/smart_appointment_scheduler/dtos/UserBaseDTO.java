package com.jatin.smart_appointment_scheduler.dtos;

import com.jatin.smart_appointment_scheduler.enums.Gender;
import com.jatin.smart_appointment_scheduler.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class UserBaseDTO {
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotNull(message = "Gender is mandatory")
    private Gender gender;
    @NotBlank(message = "Phone and Email is mandatory")
    private String email, phone;
    @NotNull(message = "DOB is mandatory")
    private LocalDate dob;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotNull(message = "Role is mandatory")
    private Role role;
}
