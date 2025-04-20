package com.GP.GP.entities;

import com.GP.GP.security.Role;
import com.GP.GP.security.Token;
import com.GP.GP.utill.Enums;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
    private List<Token> tokens;

    private Enums.StudentType studentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = true)
    private University university;

    @Column(name = "national_id", length = 14, unique = true)
    private String nationalId;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "faculty", length = 270)
    private String faculty;

    @Column(name = "level", length = 20)
    private String level;
    @Column(name = "date_of_birth", nullable = true)
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonManagedReference
    private Room room;

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

    @Column(name = "guardian_national_id" )
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

    @Column(name = "media", columnDefinition = "TEXT")
    private String media;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Penalty> penalties;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private AdmissionRequest admissionRequest;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private StudentProfile studentProfile;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

}
