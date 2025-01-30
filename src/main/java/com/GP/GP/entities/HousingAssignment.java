package com.GP.GP.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.Year;

@Entity
@Table(name = "housing_assignments")
@Data
public class HousingAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "academic_year", nullable = false)
    private Year academicYear;

    @CreationTimestamp
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
}
