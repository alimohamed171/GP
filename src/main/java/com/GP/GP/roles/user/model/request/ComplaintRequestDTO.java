package com.GP.GP.roles.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintRequestDTO {
    @NotBlank(message = "Field can not be empty!")
    private String complaintText;
}
