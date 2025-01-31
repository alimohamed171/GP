package com.GP.GP.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "universities")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "name",unique = true)
    private String name;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Building> buildings = new ArrayList<>();

    @OneToOne(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ApplicationGuidelineAndApproval applicationGuidelineAndApproval;
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HousingFee> housingFees = new ArrayList<>();

    @OneToOne(mappedBy = "university", cascade = CascadeType.ALL)
    private ApplicationDeadline applicationDeadline;
}
