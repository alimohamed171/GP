package com.GP.GP.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "housing_types")
@Data
public class HousingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_name", unique = true, nullable = false, length = 50)
    private String typeName;

    @OneToMany(mappedBy = "housingType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HousingFee> housingFees = new ArrayList<>();
}
