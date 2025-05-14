package com.GP.GP.roles.Auth.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegesAssignmentRequestDTO {
    private Integer userId;
    private List<Integer> privilegeIds;
}
