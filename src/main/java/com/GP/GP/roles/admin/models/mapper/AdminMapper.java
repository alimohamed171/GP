package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import com.GP.GP.roles.admin.models.dto.request.UniversityDTO;
import com.GP.GP.entities.ApplicationGuidelineAndApproval;
import com.GP.GP.entities.University;
import lombok.Builder;

@Builder
public class AdminMapper {
    public static ApplicationGuidelineAndApprovalDTO toApplicationGuidelineAndApprovalDTO(ApplicationGuidelineAndApproval entity) {
        return ApplicationGuidelineAndApprovalDTO.builder()
                .guidelines(entity.getGuidelines())
                .media(entity.getMedia())
                .build();
    }

    public static ApplicationGuidelineAndApproval toApplicationGuidelineAndApprovalEntity(ApplicationGuidelineAndApprovalDTO dto, University university) {
        return ApplicationGuidelineAndApproval.builder()
                .guidelines(dto.getGuidelines())
                .university(university)
                .media(dto.getMedia())
                .build();
    }

    public static UniversityDTO toUniversityDTO(University entity) {
        return UniversityDTO.builder()
                .name(entity.getName())
                .build();
    }

    public static University toUniversityEntity(UniversityDTO dto) {
        return University.builder()
                .name(dto.getName())
                .build();
    }
}
