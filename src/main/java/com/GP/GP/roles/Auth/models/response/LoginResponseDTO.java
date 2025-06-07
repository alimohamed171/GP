package com.GP.GP.roles.Auth.models.response;


import com.GP.GP.entities.User;
import com.GP.GP.security.Privilege;
import com.GP.GP.security.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    int userID;
    String username;
    String token;
    Role role;
    List<Privilege> privileges;

    public static LoginResponseDTO mapToResponseDTO(User user, String token) {
        return LoginResponseDTO.builder()
                .role(user.getRole())
                .username(user.getUsername())
                .token(token)
                .privileges(user.getPrivileges())
                .userID(user.getId()).build();
    }
}
