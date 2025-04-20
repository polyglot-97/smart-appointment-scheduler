package com.jatin.smart_appointment_scheduler.entities;

import com.jatin.smart_appointment_scheduler.enums.AppointmentType;
import com.jatin.smart_appointment_scheduler.enums.OperandType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "operand")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "operand_type", nullable = false)
    private OperandType operandType;
}
