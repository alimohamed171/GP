package com.GP.GP.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "acceptance_criteria")
public class AcceptanceCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @Column(name = "gpa_criteria")
    private Double gpaCriteria;

    @Column(name = "distance_criteria")
    private Double distanceCriteria;

    // Getters and Setters
}
