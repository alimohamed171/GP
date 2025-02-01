package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.ApplicationDeadline;
import com.GP.GP.entities.University;
import com.GP.GP.roles.admin.models.dto.request.ApplicationDeadlineDTO;
import lombok.Builder;

@Builder
public class ApplicationDeadlineMapper {
    public static ApplicationDeadline toApplicationDeadlineEntity(ApplicationDeadlineDTO dto, University university) {
        return ApplicationDeadline.builder()
                .university(university)
                .applicationStartDate(dto.getApplicationStartDate())
                .applicationEndDate(dto.getApplicationEndDate())
                .studentType(dto.getStudentType())
                .build();
    }

    public static void updateApplicationDeadlineEntity(ApplicationDeadline applicationDeadline, ApplicationDeadlineDTO dto) {
        applicationDeadline.setApplicationStartDate(dto.getApplicationStartDate());
        applicationDeadline.setApplicationEndDate(dto.getApplicationEndDate());
        applicationDeadline.setStudentType(dto.getStudentType());
    }
}
