package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class RoomResponseDTO {
    private int id;
    private String roomNumber;
    private Integer capacity;
    private Integer currentOccupancy;
    private Enums.RoomType type;
    private Enums.RoomStatus status;
    private BuildingSummaryResponseDTO building;
    private Integer floorNumber;
    private String wing;
    private Integer bedCount;
    private Integer occupiedBeds;
}
