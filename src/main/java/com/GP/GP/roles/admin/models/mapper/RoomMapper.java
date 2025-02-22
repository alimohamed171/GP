package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.Building;
import com.GP.GP.entities.Room;
import com.GP.GP.roles.admin.models.dto.request.RoomRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.BuildingResponseDTO;
import com.GP.GP.roles.admin.models.dto.response.RoomResponseDTO;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.utill.Enums;
import lombok.Builder;

@Builder
public class RoomMapper {

    public static Room toRoomEntity(RoomRequestDTO dto, Building building) {
        return Room.builder()
                .roomNumber(dto.getRoomNumber())
                .capacity(dto.getCapacity())
                .currentOccupancy(dto.getCurrentOccupancy() != null ? dto.getCurrentOccupancy() : 0)
                .type(dto.getType())
                .status(dto.getStatus() != null ? dto.getStatus() : Enums.RoomStatus.AVAILABLE)
                .building(building)
                .build();
    }

    public static RoomResponseDTO toRoomResponseDTO(Room room) {
        return RoomResponseDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .capacity(room.getCapacity())
                .currentOccupancy(room.getCurrentOccupancy())
                .type(room.getType())
                .status(room.getStatus())
                .building(BuildingResponseDTO.builder()
                        .id(room.getBuilding().getId())
                        .name(room.getBuilding().getName())
                        .type(room.getBuilding().getType())
                        .university(UniversityResponseDTO.mapToResponseDTO(room.getBuilding().getUniversity()))
                        .build())
                .build();
    }

}
