package com.GP.GP.roles.admin.models.dto.request;

import com.GP.GP.utill.Enums;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    private Integer currentOccupancy = 0;

    private Enums.RoomStatus status = Enums.RoomStatus.AVAILABLE;

    @NotNull(message = "Floor number is required.")
    @Min(value = 0, message = "Floor number must be at least 0.")
    private Integer floorNumber;

    @Pattern(regexp = "^[A-Za-z\\u0600-\\u06FF ]+$", message = "Wing must only contain alphabetic characters.")
    private String wing;

    @NotNull(message = "Bed count is required.")
    @Min(value = 1, message = "Bed count must be at least 1.")
    private Integer bedCount;

    @Min(value = 0, message = "Occupied beds cannot be negative.")
    private Integer occupiedBeds = 0;

    @NotNull(message = "Building ID is required.")
    private Integer buildingId;
}
