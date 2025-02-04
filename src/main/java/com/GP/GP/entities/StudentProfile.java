package com.GP.GP.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "student_profiles")
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "national_id", nullable = false, length = 14)
    private String nationalId;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "faculty", length = 20)
    private String faculty;

    @Column(name = "level", length = 20)
    private String level;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;


}
