package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import com.GP.GP.roles.admin.models.dto.request.UniversityDTO;
import com.GP.GP.entities.ApplicationGuidelineAndApproval;
import com.GP.GP.entities.University;
import lombok.Builder;

@Builder
public class AdminMapper {
    public static ApplicationGuidelineAndApprovalDTO toDTO(ApplicationGuidelineAndApproval entity) {
        return ApplicationGuidelineAndApprovalDTO.builder()
                .guidelines(entity.getGuidelines())
                .build();
    }

    public static ApplicationGuidelineAndApproval toEntity(ApplicationGuidelineAndApprovalDTO dto, University university) {
        return ApplicationGuidelineAndApproval.builder()
                .guidelines(dto.getGuidelines())
                .university(university)
                .build();
    }

    public static UniversityDTO toDTO(University entity) {
        return UniversityDTO.builder()
                .name(entity.getName())
                .build();
    }

    public static University toEntity(UniversityDTO dto) {
        return University.builder()
                .name(dto.getName())
                .build();
    }
}
