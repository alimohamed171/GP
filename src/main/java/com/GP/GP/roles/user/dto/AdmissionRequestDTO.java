package com.GP.GP.roles.user.dto;
import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRequestDTO {
    private int id;
    private int userId;
    private int universityId;
    private Enums.StudentType studentType;
    private String nationalId;
    private String name;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private Enums.Gender gender;
    private Enums.Religion religion;
    private String residenceAddress;
    private String detailedAddress;
    private String email;
    private String mobileNumber;
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
    private String universityName;
    private String housingInPreviousYears;
    private Boolean familyAbroad;
    private Boolean specialNeeds;
    private String secondaryDivision;
    private Float totalGradesHighSchool;
    private String passportNumber;
    private String passportIssuingAuthority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate date;

}
