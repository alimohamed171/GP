package com.GP.GP.entities;

import com.GP.GP.utill.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "admission_requests")
public class AdmissionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @OneToOne(mappedBy = "admissionRequest", fetch = FetchType.EAGER)
    private Accommodation accommodation;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "housing_type_id")
//    private String housingType;

    private Enums.StudentType studentType;

    @OneToOne
    @JoinColumn(name = "student_profile_id")
    private StudentProfile studentProfile;

    @Column(name = "residence_address",columnDefinition = "TEXT")
    private String residenceAddress;

    @Column(name = "detailed_address",columnDefinition = "TEXT")
    private String detailedAddress;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "gender")
    private Enums.Gender gender;

    @Column(name = "religion")
    private Enums.Religion religion;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "father_national_id", length = 14)
    private String fatherNationalId;

    @Column(name = "father_occupation")
    private String fatherOccupation;

    @Column(name = "father_phone_number", length = 20)
    private String fatherPhoneNumber;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "guardian_national_id", length = 14)
    private String guardianNationalId;

    @Column(name = "guardian_phone_number", length = 20)
    private String guardianPhoneNumber;

    @Column(name = "parents_status")
    private String parentsStatus;

    @Column(name = "previous_academic_year_gpa",  scale = 2)
    private Double previousAcademicYearGpa;

    @Column(name = "status")
    private Enums.AdmissionRequestStatues status;


    @Column(name = "housing_in_previous_years")
    private String housingInPreviousYears;

    @Column(name = "family_abroad")
    private Boolean familyAbroad;

    @Column(name = "special_needs")
    private Boolean specialNeeds;

    @Column(name = "secondary_division")
    private String secondaryDivision;

    @Column(name = "total_grades_high_school")
    private Float totalGradesHighSchool;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "passport_issuing_authority")
    private String passportIssuingAuthority;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "date", nullable = false)
    private LocalDate date ;
}
