package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuildingResponseDTO {
    private int id;
    private String name;
    private Enums.BuildingType type;
    private UniversityResponseDTO university;
}
