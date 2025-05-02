package com.GP.GP.roles.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentPriorityDto {
    private Integer userId;
    private String fullName;
    private String level;
    private Double gpa;
    private Float highSchoolGrade;
    private LocalDate dateOfBirth;
    private String residenceAddress;
    private boolean isNewStudent;
}
