package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.response.StudentSearchResponseDTO;

public class StudentSearchMapper {

    public static StudentSearchResponseDTO mapStudentToDTO(User user) {
        return StudentSearchResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nationalId(user.getNationalId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .faculty(user.getFaculty())
                .residenceAddress(user.getResidenceAddress())
                .detailedAddress(user.getDetailedAddress())
                .placeOfBirth(user.getPlaceOfBirth())
                .gender(user.getGender() != null ? user.getGender().name() : null)
                .religion(user.getReligion() != null ? user.getReligion().name() : null)
                .level(user.getLevel())
                .build();
    }
}
