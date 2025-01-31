package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.entities.ApplicationDeadline;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDeadlineResponseDTO {

    private int id;
    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;
    private String studentType;
    private UniversityResponseDTO universityResponseDTO;

    public static ApplicationDeadlineResponseDTO mapToResponseDTO(ApplicationDeadline entity){
        return ApplicationDeadlineResponseDTO.builder()
                .applicationEndDate(entity.getApplicationEndDate())
                .applicationStartDate(entity.getApplicationStartDate())
                .studentType(entity.getStudentType())
                .id(entity.getId())
                .universityResponseDTO(UniversityResponseDTO.mapToResponseDTO(entity.getUniversity()))
                .build();
    }
}
