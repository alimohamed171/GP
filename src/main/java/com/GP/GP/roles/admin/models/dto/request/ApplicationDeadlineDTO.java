package com.GP.GP.roles.admin.models.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDeadlineDTO {

    @NotNull(message = "Application start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDate applicationStartDate;

    @NotNull(message = "Application end date is required")
    @Future(message = "End date must be in the future")
    private LocalDate applicationEndDate;

    @NotBlank(message = "Student type is required")
    private String studentType;

    private String media;
}
