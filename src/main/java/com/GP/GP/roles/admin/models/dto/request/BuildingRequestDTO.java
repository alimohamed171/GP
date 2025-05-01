package com.GP.GP.roles.admin.models.dto.request;

import com.GP.GP.utill.Enums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequestDTO {
    @NotBlank(message = "Building name is required")
    private String name;

    @NotNull(message = "Building type is required")
    private Enums.BuildingType type;

    @NotNull(message = "Floors count is required")
    private Integer floorsCount;

    @NotNull(message = "Wings count is required")
    private Integer wingsCount;

    @NotNull(message = "University ID is required")
    private Integer universityId;
}
