package com.GP.GP.roles.Auth.models.response;


import com.GP.GP.entities.User;
import com.GP.GP.roles.user.model.dto.PrivilegesDTO;
import com.GP.GP.security.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    int userID;
    String username;
    String token;
    Role role;
    List<PrivilegesDTO> privileges;


    public static LoginResponseDTO mapToResponseDTO(User user, String token) {
        List<PrivilegesDTO> privilegeDTOs = Optional.ofNullable(user.getPrivileges())
                .orElse(List.of())
                .stream()
                .map(priv -> new PrivilegesDTO(priv.getId(), priv.getName()))
                .collect(Collectors.toList());

        return LoginResponseDTO.builder()
                .role(user.getRole())
                .username(user.getUsername())
                .token(token)
                .userID(user.getId())
                .privileges(privilegeDTOs)
                .build();
    }
}
