package com.GP.GP.roles.user.model.mapper;

import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.roles.user.model.request.AdmissionRequestInquiryDTO;
import com.GP.GP.roles.user.model.response.AdmissionRequestInquiryResponseDTO;
import lombok.Builder;

@Builder
public class AdmissionRequestInquiryMapper {
    public static User requestToEntity(AdmissionRequestInquiryDTO request) {
        if (request == null) return null;

        User admissionRequest = new User();
        admissionRequest.setNationalId(request.getNationalId());
        return admissionRequest;
    }

    public static AdmissionRequestInquiryResponseDTO entityToResponse (User admissionRequest) {
        if (admissionRequest == null) return null;

        UniversityResponseDTO universityDTO = null;
        if (admissionRequest.getUniversity() != null) {
            universityDTO = new UniversityResponseDTO(
                    admissionRequest.getUniversity().getId(),
                    admissionRequest.getUniversity().getName()
            );
        }

        return AdmissionRequestInquiryResponseDTO.builder()
                .userId(admissionRequest.getId())
                .status(admissionRequest.getStatus().name())
                .name(admissionRequest.getFirstName()+admissionRequest.getLastName())
                .username(admissionRequest.getUsername())
                .faculty(admissionRequest.getFaculty())
                .level(admissionRequest.getLevel())
                .annualGrade(admissionRequest.getAnnualGrade())
                .university(universityDTO)
                .build();

    }
}
