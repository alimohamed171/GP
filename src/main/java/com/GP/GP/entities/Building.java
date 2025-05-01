package com.GP.GP.entities;

import com.GP.GP.utill.Enums;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "buildings")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "floors_count")
    private Integer floorsCount;

    @Column(name = "wings_count")
    private Integer wingsCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Enums.BuildingType type; // MALE, FEMALE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    @JsonBackReference
    private University university;

    @OneToMany(mappedBy = "building", cascade = CascadeType.PERSIST,orphanRemoval = false)
    @JsonBackReference
    private List<Room> rooms = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Getters and Setters
}
