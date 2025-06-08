package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.response.AdminUserDTO;

public class AdminPrevMapper {
    public static AdminUserDTO toAdminUserDTO(User user) {
        return new AdminUserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getPrivileges()
        );
    }
}
