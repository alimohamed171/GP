package com.GP.GP.roles.user.model.response;

import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRequestInquiryResponseDTO {
    private Integer userId;
    private String name;
    private String status;
    private String username;
    private String faculty;
    private String level;
    private UniversityResponseDTO university;
}
