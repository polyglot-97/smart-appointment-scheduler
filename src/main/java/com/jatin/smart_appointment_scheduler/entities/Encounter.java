package com.jatin.smart_appointment_scheduler.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


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

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

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

    @Column(nullable = false)
    private LocalDateTime serviceDate;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        serviceDate = now;
    }
}
