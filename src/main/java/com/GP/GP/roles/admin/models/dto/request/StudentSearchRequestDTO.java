package com.GP.GP.roles.admin.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentSearchRequestDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "National ID is required")
    private String nationalId;
}
