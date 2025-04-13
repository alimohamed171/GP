package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.roles.admin.models.dto.response.AcceptedStudentResponseDTO;

public class AcceptedStudentMapper {
    public static AcceptedStudentResponseDTO mapToDTO(AdmissionRequest request) {
        String fullName = request.getUser().getFirstName() + " "
                + request.getUser().getFatherName() + " "
                + request.getUser().getLastName();

        return AcceptedStudentResponseDTO.builder()
                .id(request.getId())
                .studentName(fullName)
                .universityName(request.getUniversity().getName())
                .residenceAddress(request.getResidenceAddress())
                .detailedAddress(request.getDetailedAddress())
                .build();
    }
}

