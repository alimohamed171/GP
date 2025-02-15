package com.GP.GP.roles.user.model.mapper;

import com.GP.GP.entities.University;
import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.roles.user.model.request.UpdateUserRequestDTO;
import com.GP.GP.roles.user.model.response.UpdatedUserResponseDTO;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
public class UserMapper {
    public static void updateUserEntity(User existingUser, UpdateUserRequestDTO dto, University university, PasswordEncoder passwordEncoder) {
        existingUser.setFirstName(dto.getFirstName());
        existingUser.setLastName(dto.getLastName());
        existingUser.setUsername(dto.getUsername());
        existingUser.setPassword(passwordEncoder.encode(dto.getPassword())); // Encrypt password
        existingUser.setUniversity(university);
        existingUser.setNationalId(dto.getNationalId());
        existingUser.setMobileNumber(dto.getMobileNumber());
        existingUser.setFaculty(dto.getFaculty());
        existingUser.setLevel(dto.getLevel());
        existingUser.setDateOfBirth(dto.getDateOfBirth());
       // existingUser.setRoomId(dto.getRoomId());
        existingUser.setStudentType(dto.getStudentType());
        existingUser.setResidenceAddress(dto.getResidenceAddress());
        existingUser.setDetailedAddress(dto.getDetailedAddress());
        existingUser.setPlaceOfBirth(dto.getPlaceOfBirth());
        existingUser.setGender(dto.getGender());
        existingUser.setReligion(dto.getReligion());
        existingUser.setFatherName(dto.getFatherName());
        existingUser.setFatherNationalId(dto.getFatherNationalId());
        existingUser.setFatherOccupation(dto.getFatherOccupation());
        existingUser.setFatherPhoneNumber(dto.getFatherPhoneNumber());
        existingUser.setGuardianName(dto.getGuardianName());
        existingUser.setGuardianNationalId(dto.getGuardianNationalId());
        existingUser.setGuardianPhoneNumber(dto.getGuardianPhoneNumber());
        existingUser.setParentsStatus(dto.getParentsStatus());
        existingUser.setPreviousAcademicYearGpa(dto.getPreviousAcademicYearGpa());
        existingUser.setStatus(dto.getStatus());
        existingUser.setHousingInPreviousYears(dto.getHousingInPreviousYears());
        existingUser.setFamilyAbroad(dto.getFamilyAbroad());
        existingUser.setSpecialNeeds(dto.getSpecialNeeds());
        existingUser.setSecondaryDivision(dto.getSecondaryDivision());
        existingUser.setTotalGradesHighSchool(dto.getTotalGradesHighSchool());
        existingUser.setPassportNumber(dto.getPassportNumber());
        existingUser.setPassportIssuingAuthority(dto.getPassportIssuingAuthority());
    }
    public static UpdatedUserResponseDTO mapToUpdatedUserResponseDTO(User user) {
        return UpdatedUserResponseDTO.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .university(new UniversityResponseDTO(user.getUniversity().getId(),user.getUniversity().getName()))
                .nationalId(user.getNationalId())
                .mobileNumber(user.getMobileNumber())
                .faculty(user.getFaculty())
                .level(user.getLevel())
                .dateOfBirth(user.getDateOfBirth())
//                .roomId(null) // If not needed, set it as null
                .studentType(user.getStudentType())
                .residenceAddress(user.getResidenceAddress())
                .detailedAddress(user.getDetailedAddress())
                .placeOfBirth(user.getPlaceOfBirth())
                .gender(user.getGender())
                .religion(user.getReligion())
                .fatherName(user.getFatherName())
                .fatherNationalId(user.getFatherNationalId())
                .fatherOccupation(user.getFatherOccupation())
                .fatherPhoneNumber(user.getFatherPhoneNumber())
                .guardianName(user.getGuardianName())
                .guardianNationalId(user.getGuardianNationalId())
                .guardianPhoneNumber(user.getGuardianPhoneNumber())
                .parentsStatus(user.getParentsStatus())
                .previousAcademicYearGpa(user.getPreviousAcademicYearGpa())
                .status(user.getStatus())
                .housingInPreviousYears(user.getHousingInPreviousYears())
                .familyAbroad(user.getFamilyAbroad())
                .specialNeeds(user.getSpecialNeeds())
                .secondaryDivision(user.getSecondaryDivision())
                .totalGradesHighSchool(user.getTotalGradesHighSchool())
                .passportNumber(user.getPassportNumber())
                .passportIssuingAuthority(user.getPassportIssuingAuthority())
                .build();
    }
}
