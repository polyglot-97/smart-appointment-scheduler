package com.jatin.smart_appointment_scheduler.entities;

import com.jatin.smart_appointment_scheduler.enums.Specialization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Provider extends User{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialization specilization;

    @Column(nullable = false)
    private String npiNumber;
}
