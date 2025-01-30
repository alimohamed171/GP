package com.GP.GP.entities;

import com.GP.GP.utill.Enums;
import jakarta.persistence.*;

@Entity
@Table(name = "student_types")
public class StudentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type", unique = true)
    private Enums.StudentType type;


    // Getters and Setters
}
