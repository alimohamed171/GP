package com.GP.GP.roles.admin.models.dto.request;

import com.GP.GP.utill.Enums;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDTO {
    @NotBlank(message = "Room number is required.")
    private String roomNumber;

    @NotNull(message = "Capacity is required.")
    @Min(value = 1, message = "Capacity must be at least 1.")
    private Integer capacity;

    @NotNull(message = "Room type is required.")
    private Enums.RoomType type;

    private Integer currentOccupancy;

    private Enums.RoomStatus status;

    @NotNull(message = "Building ID is required.")
    private Integer buildingId;
}
