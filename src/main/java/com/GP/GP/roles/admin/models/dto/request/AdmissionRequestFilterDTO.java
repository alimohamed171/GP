package com.GP.GP.roles.admin.models.dto.request;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRequestFilterDTO {
    private List<Enums.AdmissionRequestStatues> status;
    private Enums.Gender gender;
    private List<String> universityName;
    private List<String> faculty;
    private List<String>   level;
    private Boolean specialNeeds;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Enums.StudentType studentType;
    private List<Enums.SecurityCheckStatues> securityCheck;
}
