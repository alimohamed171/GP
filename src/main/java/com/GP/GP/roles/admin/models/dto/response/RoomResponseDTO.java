package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.utill.Enums;

public class RoomResponseDTO {
    private int id;
    private String roomNumber;
    private Integer capacity;
    private Integer currentOccupancy;
    private Enums.RoomType type;
    private Enums.RoomStatus status;
    private BuildingResponseDTO building;
}
