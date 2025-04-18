package com.GP.GP.roles.admin.models.dto.request;

import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRequestExportDtO {
    private String fullName;
    private String email;
    private String universityName;
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
    private Double previousAcademicYearGpa;
    private Enums.AdmissionRequestStatues status;
    private String housingInPreviousYears;
    private Boolean familyAbroad;
    private Boolean specialNeeds;
    private String secondaryDivision;
    private Float totalGradesHighSchool;
    private String passportNumber;
    private String passportIssuingAuthority;
    private Enums.SecurityCheckStatues securityCheck;
}
