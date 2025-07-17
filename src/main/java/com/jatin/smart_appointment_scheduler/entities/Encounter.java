package com.jatin.smart_appointment_scheduler.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;


@Entity
@Table(name = "encounters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "encounter_cpt_codes", joinColumns = @JoinColumn(name = "encounter_id"))
    @Column(name = "cpt_code")
    private List<String> cptCodes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "encounter_icd_codes", joinColumns = @JoinColumn(name = "encounter_id"))
    @Column(name = "icd_code")
    private List<String> icdCodes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "encounter_modifiers", joinColumns = @JoinColumn(name = "encounter_id"))
    @Column(name = "modifier")
    private List<String> modifiers;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "encounter_units", joinColumns = @JoinColumn(name = "encounter_id"))
    @Column(name = "unit")
    private List<Integer> units;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime serviceDate;

    @Column(nullable = false)
    private Boolean isActive = true;
}
