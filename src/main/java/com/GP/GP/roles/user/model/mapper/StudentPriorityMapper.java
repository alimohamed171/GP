package com.GP.GP.roles.user.model.mapper;

import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.roles.user.model.dto.StudentDto;
import com.GP.GP.roles.user.model.dto.StudentPriorityDto;
import org.springframework.stereotype.Component;

@Component
public class StudentPriorityMapper {
    public static StudentPriorityDto toDto(User user) {
        return StudentPriorityDto.builder()
                .userId(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .level(user.getLevel())
                .gpa(user.getPreviousAcademicYearGpa())
                .highSchoolGrade(user.getTotalGradesHighSchool())
                .dateOfBirth(user.getDateOfBirth())
                .residenceAddress(user.getResidenceAddress())
                .isNewStudent("1".equals(user.getLevel()))
                .build();
    }
}