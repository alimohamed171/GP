package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.entities.University;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniversityResponseDTO {
    private int id;
    private String name;

    public static UniversityResponseDTO mapToResponseDTO(University university) {
        return UniversityResponseDTO.builder()
                .id(university.getId())
                .name(university.getName())
                .build();
    }

}
