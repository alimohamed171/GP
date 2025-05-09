package com.GP.GP.roles.admin.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderAnalysisDTO {
    private int underReview = 0;
    private int accepted = 0;
    private int rejected = 0;
    private int total = 0;
}
