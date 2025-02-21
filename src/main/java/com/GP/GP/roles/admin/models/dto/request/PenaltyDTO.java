package com.GP.GP.roles.admin.models.dto.request;

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
public class PenaltyDTO {
    @NotBlank(message = "Title cannot be blank")
    private String penaltyTitle ;

    @NotBlank(message = "Reason cannot be blank")
    private String reason;

    @NotNull(message = "Issuing date cannot be null")
    private LocalDate dateIssued;

    @NotNull(message = "User ID cannot be null")
    private Integer userId;
}
