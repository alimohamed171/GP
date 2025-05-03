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
public class RoomAssignmentResponseDTO {
    private String studentName;
    private int roomId;
    private int buildingId;
    private String roomNumber;
    private String buildingName;
    private Enums.RoomType roomType;
    private String wing;
    private int floorNumber;
    private int bedNumber;

}
