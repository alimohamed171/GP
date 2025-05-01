package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingSummaryResponseDTO {
        private Integer id;
        private String name;
        private Enums.BuildingType type;
        private UniversityResponseDTO university;

}
