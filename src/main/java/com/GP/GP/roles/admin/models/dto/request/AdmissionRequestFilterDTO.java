package com.GP.GP.roles.admin.models.dto.request;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRequestFilterDTO {
    private Enums.AdmissionRequestStatues status;
    private Enums.Gender gender;
    private String universityName;
    private String faculty;
    private String  level;
    private Boolean specialNeeds;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String studentType;
}
