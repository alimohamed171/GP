package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.Building;
import com.GP.GP.entities.University;
import com.GP.GP.roles.admin.models.dto.request.BuildingRequestDTO;
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
                .floorsCount(building.getFloorsCount())
                .wingsCount(building.getWingsCount())
                .university(UniversityResponseDTO.mapToResponseDTO(building.getUniversity()))
                .build();
    }

    public static Building mapBuildingRequestToEntity(BuildingRequestDTO dto, University university) {
        return Building.builder()
                .name(dto.getName())
                .type(dto.getType())
                .wingsCount(dto.getWingsCount())
                .floorsCount(dto.getFloorsCount())
                .university(university)
                .build();
    }
}
