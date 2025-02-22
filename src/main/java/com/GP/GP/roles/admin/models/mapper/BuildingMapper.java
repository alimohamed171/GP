package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.Building;
import com.GP.GP.roles.admin.models.dto.response.BuildingResponseDTO;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import lombok.Builder;

@Builder
public class BuildingMapper {

    public static BuildingResponseDTO mapBuildingResponseToDto(Building building) {
        return BuildingResponseDTO.builder()
                .id(building.getId())
                .name(building.getName())
                .type(building.getType())
                .university(UniversityResponseDTO.mapToResponseDTO(building.getUniversity()))
                .build();
    }
}
