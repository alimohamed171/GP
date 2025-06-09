package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.response.AdminUserDTO;
import com.GP.GP.roles.user.model.dto.PrivilegesDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminPrevMapper {
    public static AdminUserDTO toAdminUserDTO(User user) {
        List<PrivilegesDTO> privilegesDTOList = Optional.ofNullable(user.getPrivileges())
                .orElse(List.of())
                .stream()
                .map(priv -> new PrivilegesDTO(priv.getId(), priv.getName()))
                .collect(Collectors.toList());

        return new AdminUserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                privilegesDTOList
        );
    }
}