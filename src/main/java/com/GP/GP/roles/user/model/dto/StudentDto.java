package com.GP.GP.roles.user.model.dto;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String mobileNumber;
    private String nationalId;
    private Enums.StudentType studentType;
    private String faculty;
    private String level;
    private LocalDate dateOfBirth;
    private String residenceAddress;
    private String detailedAddress;
    private String placeOfBirth;
    private Enums.Gender gender;
    private Enums.Religion religion;

    // Father Info
    private String fatherName;
    private String fatherNationalId;
    private String fatherOccupation;
    private String fatherPhoneNumber;

    // Guardian Info
    private String guardianName;
    private String guardianNationalId;
    private String guardianPhoneNumber;

    // Education Info
    private String parentsStatus;
    private Double previousAcademicYearGpa;
    private Enums.AnnualGrade annualGrade;
    private String secondaryDivision;
    private Float totalGradesHighSchool;

    // Admission Info
    private Enums.AdmissionRequestStatues status;
    private String housingInPreviousYears;
    private Boolean familyAbroad;
    private Boolean specialNeeds;

    // Passport Info
    private String passportNumber;
    private String passportIssuingAuthority;

    // Misc
    private String media;
    private Enums.SecurityCheckStatues securityCheck;
    private String note;
    private LocalDateTime createdAt;
}
