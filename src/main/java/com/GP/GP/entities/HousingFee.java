package com.GP.GP.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "housing_fees")
public class HousingFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "university_name")
    private String universityName;


    @Column(name = "fees")
    private int fees;

    @ManyToOne
    @JoinColumn(name = "housing_type_id", nullable = false)
    private HousingType housingType;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;
    // Getters and Setters
}
