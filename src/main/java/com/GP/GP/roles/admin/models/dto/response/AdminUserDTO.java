package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.roles.user.model.dto.PrivilegesDTO;
import com.GP.GP.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDTO {
    private int userID;
    private String username;
    private Role role;
    private List<PrivilegesDTO> privileges;
}
