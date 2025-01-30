package com.GP.GP.roles.user.mapper;

import com.GP.GP.entities.University;
import com.GP.GP.roles.user.dto.AdmissionRequestDTO;
import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.entities.User;

public class AdmissionRequestMapper {
    public static AdmissionRequestDTO toDTO(AdmissionRequest entity) {
        return AdmissionRequestDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .universityId(entity.getUniversity().getId())
                .housingType(entity.getHousingType())
                .studentType(entity.getStudentType())
                .nationalId(entity.getNationalId())
                .name(entity.getName())
                .dateOfBirth(entity.getDateOfBirth())
                .placeOfBirth(entity.getPlaceOfBirth())
                .gender(entity.getGender())
                .religion(entity.getReligion())
                .residenceAddress(entity.getResidenceAddress())
                .detailedAddress(entity.getDetailedAddress())
                .email(entity.getEmail())
                .mobileNumber(entity.getMobileNumber())
                .fatherName(entity.getFatherName())
                .fatherNationalId(entity.getFatherNationalId())
                .fatherOccupation(entity.getFatherOccupation())
                .fatherPhoneNumber(entity.getFatherPhoneNumber())
                .guardianName(entity.getGuardianName())
                .guardianNationalId(entity.getGuardianNationalId())
                .guardianPhoneNumber(entity.getGuardianPhoneNumber())
                .parentsStatus(entity.getParentsStatus())
                .previousAcademicYearGpa(entity.getPreviousAcademicYearGpa())
                .status(entity.getStatus())
                .universityName(entity.getUniversityName())
                .housingInPreviousYears(entity.getHousingInPreviousYears())
                .familyAbroad(entity.getFamilyAbroad())
                .specialNeeds(entity.getSpecialNeeds())
                .secondaryDivision(entity.getSecondaryDivision())
                .totalGradesHighSchool(entity.getTotalGradesHighSchool())
                .passportNumber(entity.getPassportNumber())
                .passportIssuingAuthority(entity.getPassportIssuingAuthority())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .distance(entity.getDistance())
                .date(entity.getDate())
                .password(entity.getPassword())
                .build();
    }

    public static AdmissionRequest toEntity(AdmissionRequestDTO dto, User user, University university) {
        AdmissionRequest entity = new AdmissionRequest();
        entity.setId(dto.getId());
        entity.setUser(user);
        entity.setUniversity(university);
        entity.setHousingType(dto.getHousingType());
        entity.setStudentType(dto.getStudentType());
        entity.setNationalId(dto.getNationalId());
        entity.setName(dto.getName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setPlaceOfBirth(dto.getPlaceOfBirth());
        entity.setGender(dto.getGender());
        entity.setReligion(dto.getReligion());
        entity.setResidenceAddress(dto.getResidenceAddress());
        entity.setDetailedAddress(dto.getDetailedAddress());
        entity.setEmail(dto.getEmail());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setFatherName(dto.getFatherName());
        entity.setFatherNationalId(dto.getFatherNationalId());
        entity.setFatherOccupation(dto.getFatherOccupation());
        entity.setFatherPhoneNumber(dto.getFatherPhoneNumber());
        entity.setGuardianName(dto.getGuardianName());
        entity.setGuardianNationalId(dto.getGuardianNationalId());
        entity.setGuardianPhoneNumber(dto.getGuardianPhoneNumber());
        entity.setParentsStatus(dto.getParentsStatus());
        entity.setPreviousAcademicYearGpa(dto.getPreviousAcademicYearGpa());
        entity.setStatus(dto.getStatus());
        entity.setUniversityName(dto.getUniversityName());
        entity.setHousingInPreviousYears(dto.getHousingInPreviousYears());
        entity.setFamilyAbroad(dto.getFamilyAbroad());
        entity.setSpecialNeeds(dto.getSpecialNeeds());
        entity.setSecondaryDivision(dto.getSecondaryDivision());
        entity.setTotalGradesHighSchool(dto.getTotalGradesHighSchool());
        entity.setPassportNumber(dto.getPassportNumber());
        entity.setPassportIssuingAuthority(dto.getPassportIssuingAuthority());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setDistance(dto.getDistance());
        entity.setDate(dto.getDate());
        entity.setPassword(dto.getPassword());
        return entity;
    }

}
