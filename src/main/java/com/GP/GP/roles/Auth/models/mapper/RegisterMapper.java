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
            User.UserBuilder userBuilder = builder
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
                    .university(university)
                    .media(dto.getMedia())
                    .annualGrade(dto.getAnnualGrade())
                    .studentCode(dto.getStudentCode())
                    .guardianRelationship(dto.getGuardianRelationship())
                    .phoneNumber(dto.getPhoneNumber())
                    .wantFood(dto.getWantFood())
                    .houseTypeName(dto.getHouseTypeName());
        }
        return builder.build();
    }

    public static void updateUserEntity(User user, RegisterRequestDTO dto, University university) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setNationalId(dto.getNationalId());
        user.setMobileNumber(dto.getMobileNumber());
        user.setFaculty(dto.getFaculty());
        user.setLevel(dto.getLevel());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setResidenceAddress(dto.getResidenceAddress());
        user.setDetailedAddress(dto.getDetailedAddress());
        user.setPlaceOfBirth(dto.getPlaceOfBirth());
        user.setGender(dto.getGender());
        user.setReligion(dto.getReligion());
        user.setFatherName(dto.getFatherName());
        user.setFatherNationalId(dto.getFatherNationalId());
        user.setFatherOccupation(dto.getFatherOccupation());
        user.setFatherPhoneNumber(dto.getFatherPhoneNumber());
        user.setGuardianName(dto.getGuardianName());
        user.setGuardianNationalId(dto.getGuardianNationalId());
        user.setGuardianPhoneNumber(dto.getGuardianPhoneNumber());
        user.setParentsStatus(dto.getParentsStatus());
        user.setPreviousAcademicYearGpa(dto.getPreviousAcademicYearGpa());
        user.setStatus(dto.getStatus());
        user.setSecurityCheck(dto.getSecurityCheck());
        user.setHousingInPreviousYears(dto.getHousingInPreviousYears());
        user.setFamilyAbroad(dto.getFamilyAbroad());
        user.setSpecialNeeds(dto.getSpecialNeeds());
        user.setSecondaryDivision(dto.getSecondaryDivision());
        user.setTotalGradesHighSchool(dto.getTotalGradesHighSchool());
        user.setPassportNumber(dto.getPassportNumber());
        user.setPassportIssuingAuthority(dto.getPassportIssuingAuthority());
        user.setUniversity(university);
        user.setMedia(dto.getMedia());
        user.setAnnualGrade(dto.getAnnualGrade());
        user.setStudentCode(dto.getStudentCode());
        user.setGuardianRelationship(dto.getGuardianRelationship());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setWantFood(dto.getWantFood());
        user.setHouseTypeName(dto.getHouseTypeName());
        user.setRole(dto.getRole());
    }


}
