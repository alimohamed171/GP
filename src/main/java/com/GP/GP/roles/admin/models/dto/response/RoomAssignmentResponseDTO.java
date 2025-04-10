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
    private int roomId;
    private int buildingId;
    private String roomNumber;
    private Enums.RoomType roomType;
    private String buildingName;
    private String studentName;
    private String studentEmail;
    private String studentPhoneNumber;
    private Enums.Gender studentGender;
    private String faculty;
    private String level;
}
