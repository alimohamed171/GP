package com.GP.GP.roles.user.model.dto;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    private int id;
    private Integer admissionRequestId;
    private Integer studentProfileId;
    private Integer housingTypeId;
    private Integer roomId;
    private Enums.AccommodationStatus status;
}
