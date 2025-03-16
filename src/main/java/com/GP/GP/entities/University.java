package com.GP.GP.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<Building> buildings = new ArrayList<>();

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY)// تغيير من OneToOne إلى OneToMany
    @JsonManagedReference
    private List<ApplicationGuidelineAndApproval> applicationGuidelines = new ArrayList<>();

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HousingFee> housingFees = new ArrayList<>();
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ApplicationDeadline> applicationDeadlines;
}
