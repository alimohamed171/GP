package com.GP.GP.roles.Auth.models.request;

import com.GP.GP.security.Role;
import com.GP.GP.utill.Enums;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDTO {

    private String firstName;
    private String lastName;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

    private Integer universityId;
    private String nationalId;
    private String mobileNumber;
    private String faculty;
    private String level;
    private LocalDate dateOfBirth;
    private Enums.StudentType studentType;
    private String residenceAddress;
    private String detailedAddress;
    private String placeOfBirth;
    private Enums.Gender gender;
    private Enums.Religion religion;
    private String fatherName;
    private String fatherNationalId;
    private String fatherOccupation;
    private String fatherPhoneNumber;
    private String guardianName;
    private String guardianNationalId;
    private String guardianPhoneNumber;
    private String parentsStatus;
    private Double previousAcademicYearGpa;
    private Enums.AdmissionRequestStatues status;
    private String housingInPreviousYears;
    private Boolean familyAbroad;
    private Boolean specialNeeds;
    private String secondaryDivision;
    private Float totalGradesHighSchool;
    private String passportNumber;
    private String passportIssuingAuthority;
    private String media;
    private Enums.SecurityCheckStatues securityCheck;
    private Enums.AnnualGrade annualGrade;
}