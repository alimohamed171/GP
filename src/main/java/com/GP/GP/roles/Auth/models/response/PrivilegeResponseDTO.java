package com.GP.GP.roles.Auth.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrivilegeResponseDTO {
    private Integer id;
    private String privilegeName;
}
