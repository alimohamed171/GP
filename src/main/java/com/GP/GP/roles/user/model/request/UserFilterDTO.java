package com.GP.GP.roles.user.model.request;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilterDTO {
    private List<Enums.AdmissionRequestStatues> status;
    private List<Enums.SecurityCheckStatues> securityCheck;
    private Boolean hasPenalty;
}
