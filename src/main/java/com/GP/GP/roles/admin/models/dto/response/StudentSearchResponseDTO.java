package com.GP.GP.roles.admin.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentSearchResponseDTO {
    private Integer id;
    private String username;
    private String nationalId;
    private String firstName;
    private String lastName;
    private String faculty;
    private String residenceAddress;
    private String detailedAddress;
    private String placeOfBirth;
    private String gender;
    private String religion;
    private String level;
}
