package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.entities.ApplicationGuidelineAndApproval;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationGuidelineAndApprovalResponseDTO {
    private int guideLinesId;
    private int universityId;
    private String universityName;
    private String guidelines;
    private String media;

    static public ApplicationGuidelineAndApprovalResponseDTO mapToResponseDTO(ApplicationGuidelineAndApproval guidelineEntity) {
        return ApplicationGuidelineAndApprovalResponseDTO.builder()
                .guideLinesId(guidelineEntity.getId())
                .guidelines(guidelineEntity.getGuidelines())
                .universityId(guidelineEntity.getUniversity().getId())
                .universityName(guidelineEntity.getUniversity().getName())
                .media(guidelineEntity.getMedia())
                .build();
    }
}
