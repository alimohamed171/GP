package com.GP.GP.roles.Auth.models.response;


import com.GP.GP.entities.University;
import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.security.Role;
import com.GP.GP.utill.Enums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponseDTO {

    private Enums.AdmissionRequestStatues status;
    private String firstName;
    int userID;
    private String lastName;
    private String username;
    private Role role;
    private UniversityResponseDTO university;
    private String faculty;
    private String level;
    private String mobileNumber;
    private Enums.Gender gender;
    private String token;

    public static RegisterResponseDTO mapToRegisterResponseDTO(User user, String token, University university) {
        return RegisterResponseDTO.builder()
                .status(user.getStatus())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole())
                .university(UniversityResponseDTO.mapToResponseDTO(user.getUniversity()))
                .faculty(user.getFaculty())
                .level(user.getLevel())
                .mobileNumber(user.getMobileNumber())
                .gender(user.getGender())
                .token(token)
                .userID(user.getId())
                .build();
    }

}
