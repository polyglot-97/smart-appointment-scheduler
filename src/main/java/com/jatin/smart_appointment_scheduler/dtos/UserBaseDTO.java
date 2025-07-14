package com.jatin.smart_appointment_scheduler.dtos;

import com.jatin.smart_appointment_scheduler.enums.Gender;
import com.jatin.smart_appointment_scheduler.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class UserBaseDTO {
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    //@NotBlank(message = "Gender is mandatory")
    private Gender gender;
    @NotBlank(message = "Phone and Email is mandatory")
    private String email, phone;
    //@NotBlank(message = "DOB is mandatory")
    private LocalDate dob;
    @NotBlank(message = "Password is mandatory")
    private String password;
    //@NotBlank(message = "Role is mandatory")
    private Role role;
}
