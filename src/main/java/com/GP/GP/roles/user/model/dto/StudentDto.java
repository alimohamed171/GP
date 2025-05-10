package com.GP.GP.roles.user.model.dto;

import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.security.Role;
import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Enums.AdmissionRequestStatues status;
    private String firstName;
    int userId;
    private String nationalId;
    private String lastName;
    private String username;
    private Role role;
    private UniversityResponseDTO university;
    private String faculty;
    private String level;
    private String mobileNumber;
    private Enums.Gender gender;
    private Enums.SecurityCheckStatues securityCheck;
    private String media;
    private String residenceAddress;
    private String AdmissionRequestStatusNotes;
}
