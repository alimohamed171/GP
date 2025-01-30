package com.GP.GP.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_absences")
public class UserAbsence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "absence_date")
    private String absenceDate;

    @Column(name = "absence_type")
    private String absenceType;

    @Column(name = "absence_reason")
    private String absenceReason;

    // Getters and Setters
}
