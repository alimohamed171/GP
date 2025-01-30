package com.GP.GP.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Category")
    private int category;

    @ManyToOne
    @JoinColumn(name = "University_ID")
    private University university;

    // Getters and Setters
}
