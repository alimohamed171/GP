package com.GP.GP.roles.user.model.mapper;

import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.roles.user.model.dto.StudentDto;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public static StudentDto toDto(User user) {
        StudentDto dto = new StudentDto();
        dto.setUserID(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setFaculty(user.getFaculty());
        dto.setLevel(user.getLevel());
        dto.setGender(user.getGender());
        dto.setStatus(user.getStatus());
        dto.setMedia(user.getMedia());
        dto.setSecurityCheck(user.getSecurityCheck());
        dto.setRole(user.getRole());
        dto.setUniversity(UniversityResponseDTO.mapToResponseDTO(user.getUniversity()));
        return dto;
    }
}

