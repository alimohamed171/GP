package com.GP.GP.roles.Auth.models.mapper;

import com.GP.GP.entities.Room;
import com.GP.GP.entities.University;
import com.GP.GP.entities.User;
import com.GP.GP.roles.Auth.models.request.RegisterRequestDTO;
import com.GP.GP.security.Role;
import lombok.Builder;

@Builder
public class RegisterMapper {
    public static User toUserEntity(RegisterRequestDTO dto, University university) {
        User.UserBuilder builder = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword()) // Password should be encoded before saving
                .role(dto.getRole());

        if (dto.getRole() != Role.ADMIN) {
            builder
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .nationalId(dto.getNationalId())
                    .mobileNumber(dto.getMobileNumber())
                    .faculty(dto.getFaculty())
                    .level(dto.getLevel())
                    .dateOfBirth(dto.getDateOfBirth())
                    .residenceAddress(dto.getResidenceAddress())
                    .detailedAddress(dto.getDetailedAddress())
                    .placeOfBirth(dto.getPlaceOfBirth())
                    .gender(dto.getGender())
                    .religion(dto.getReligion())
                    .fatherName(dto.getFatherName())
                    .fatherNationalId(dto.getFatherNationalId())
                    .fatherOccupation(dto.getFatherOccupation())
                    .fatherPhoneNumber(dto.getFatherPhoneNumber())
                    .guardianName(dto.getGuardianName())
                    .guardianNationalId(dto.getGuardianNationalId())
                    .guardianPhoneNumber(dto.getGuardianPhoneNumber())
                    .parentsStatus(dto.getParentsStatus())
                    .previousAcademicYearGpa(dto.getPreviousAcademicYearGpa())
                    .status(dto.getStatus())
                    .securityCheck(dto.getSecurityCheck())
                    .housingInPreviousYears(dto.getHousingInPreviousYears())
                    .familyAbroad(dto.getFamilyAbroad())
                    .specialNeeds(dto.getSpecialNeeds())
                    .secondaryDivision(dto.getSecondaryDivision())
                    .totalGradesHighSchool(dto.getTotalGradesHighSchool())
                    .passportNumber(dto.getPassportNumber())
                    .passportIssuingAuthority(dto.getPassportIssuingAuthority())
                    .university(university);// Keep university assignment
        }
        return builder.build();
    }

}
