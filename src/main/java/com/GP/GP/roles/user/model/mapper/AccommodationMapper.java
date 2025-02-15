package com.GP.GP.roles.user.model.mapper;

import com.GP.GP.entities.*;
import com.GP.GP.roles.user.model.dto.AccommodationDTO;
import lombok.Builder;

@Builder
public class AccommodationMapper {
    public static AccommodationDTO toDTO(Accommodation accommodation) {
        if (accommodation == null) {
            return null;
        }
        return AccommodationDTO.builder()
                .id(accommodation.getId())
                .admissionRequestId(accommodation.getAdmissionRequest() != null ? accommodation.getAdmissionRequest().getId() : null)
                .studentProfileId(accommodation.getStudentProfile() != null ? accommodation.getStudentProfile().getId() : null)
                .housingTypeId(accommodation.getHousingType() != null ? accommodation.getHousingType().getId() : null)
                .roomId(accommodation.getRoom() != null ? accommodation.getRoom().getId() : null)
                .status(accommodation.getStatus())
                .build();
    }

    public static Accommodation toEntity(AccommodationDTO dto, AdmissionRequest admissionRequest, StudentProfile studentProfile, HousingType housingType, Room room) {
        if (dto == null) {
            return null;
        }

        return Accommodation.builder()
                .id(dto.getId())
                .admissionRequest(admissionRequest)
                .studentProfile(studentProfile)
                .housingType(housingType)
                .room(room)
                .status(dto.getStatus())
                .build();
    }
}
