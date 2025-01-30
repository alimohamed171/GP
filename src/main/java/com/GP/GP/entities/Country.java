package com.GP.GP.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Country_Name")
    private String countryName;

    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private StudentType studentType;

    @Column(name = "Distance")
    private double distance;
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<City> cities = new ArrayList<>();

    // Getters and Setters
}
