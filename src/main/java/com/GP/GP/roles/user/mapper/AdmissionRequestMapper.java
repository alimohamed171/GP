package com.GP.GP.roles.user.mapper;

import com.GP.GP.entities.University;
import com.GP.GP.roles.user.dto.AdmissionRequestDTO;
import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.entities.User;
import lombok.Builder;

@Builder
public class AdmissionRequestMapper {
    public static AdmissionRequestDTO toDTO(AdmissionRequest entity) {
        return AdmissionRequestDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .universityId(entity.getUniversity().getId())
                .studentType(entity.getStudentType())
                .placeOfBirth(entity.getPlaceOfBirth())
                .gender(entity.getGender())
                .religion(entity.getReligion())
                .residenceAddress(entity.getResidenceAddress())
                .detailedAddress(entity.getDetailedAddress())
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
                .housingInPreviousYears(entity.getHousingInPreviousYears())
                .familyAbroad(entity.getFamilyAbroad())
                .specialNeeds(entity.getSpecialNeeds())
                .secondaryDivision(entity.getSecondaryDivision())
                .totalGradesHighSchool(entity.getTotalGradesHighSchool())
                .passportNumber(entity.getPassportNumber())
                .passportIssuingAuthority(entity.getPassportIssuingAuthority())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .date(entity.getDate())
                .build();
    }

    public static AdmissionRequest toEntity(AdmissionRequestDTO dto, User user, University university) {
        return AdmissionRequest.builder()
                .id(dto.getId())
                .user(user)
                .university(university)
                .studentType(dto.getStudentType())
                .placeOfBirth(dto.getPlaceOfBirth())
                .gender(dto.getGender())
                .religion(dto.getReligion())
                .residenceAddress(dto.getResidenceAddress())
                .detailedAddress(dto.getDetailedAddress())
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
                .housingInPreviousYears(dto.getHousingInPreviousYears())
                .familyAbroad(dto.getFamilyAbroad())
                .specialNeeds(dto.getSpecialNeeds())
                .secondaryDivision(dto.getSecondaryDivision())
                .totalGradesHighSchool(dto.getTotalGradesHighSchool())
                .passportNumber(dto.getPassportNumber())
                .passportIssuingAuthority(dto.getPassportIssuingAuthority())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .date(dto.getDate())
                .build();


    }

}
