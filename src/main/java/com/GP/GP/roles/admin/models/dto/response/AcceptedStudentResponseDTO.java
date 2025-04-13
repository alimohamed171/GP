package com.GP.GP.roles.admin.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcceptedStudentResponseDTO {
    private int id;
    private String studentName;
    private String universityName;
    private String residenceAddress;
    private String detailedAddress;
}
