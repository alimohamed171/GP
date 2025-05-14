package com.GP.GP.roles.Auth.models.mapper;
import com.GP.GP.security.Privilege;
import com.GP.GP.roles.Auth.models.response.PrivilegeResponseDTO;

public class PrivilegeMapper {
    public static PrivilegeResponseDTO mapToResponse(Privilege privilege) {
        return PrivilegeResponseDTO.builder()
                .id(privilege.getId())
                .privilegeName(privilege.getName())
                .build();

    }

}
