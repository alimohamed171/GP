package com.GP.GP.roles.user.model.request;

import com.GP.GP.utill.Enums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequestDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "University ID is required")
    private Integer universityId;

    @NotBlank(message = "National ID is required")
    @Size(min = 14, max = 14, message = "National ID must be 14 characters")
    private String nationalId;

    @NotBlank(message = "Mobile number is required")
    @Size(max = 20, message = "Mobile number can't exceed 20 characters")
    private String mobileNumber;

    private String faculty;

    private String level;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    private Integer roomId;

    private Enums.StudentType studentType;

    private String residenceAddress;

    private String detailedAddress;

    private String placeOfBirth;

    @NotNull(message = "Gender is required")
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
}
