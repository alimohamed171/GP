package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.roles.admin.models.dto.request.GenderAnalysisDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderAnalysisResponseDTO {
    private GenderAnalysisDTO male = new GenderAnalysisDTO();
    private GenderAnalysisDTO female = new GenderAnalysisDTO();
}
