package com.jatin.smart_appointment_scheduler.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "starting_step_id", nullable = false)
    private Step startingStep;

    @Column(nullable = false)
    private String passAction;

    private boolean isActive = true;
}
