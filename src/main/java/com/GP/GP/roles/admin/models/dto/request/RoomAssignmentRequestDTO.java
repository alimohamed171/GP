package com.GP.GP.roles.admin.models.dto.request;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomAssignmentRequestDTO {
    private int userId;
    private Enums.RoomType roomType;
}
