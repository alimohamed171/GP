package com.GP.GP.roles.admin.models.dto.request;

import com.GP.GP.utill.Enums;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Building type is required")
    private Enums.BuildingType type;

    @NotBlank(message = "University ID is required")
    private Integer universityId;
}
