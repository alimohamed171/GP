package com.GP.GP.entities;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "acceptance_criteria")
public class ApplicationDeadline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false, unique = true)
    private University university;

    @Column(name = "application_start_date")
    private LocalDate applicationStartDate;

    @Column(name = "application_end_date")
    private LocalDate applicationEndDate;

    @Column(name = "student_type")
    private String studentType;

}
