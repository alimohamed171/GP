package com.GP.GP.roles.user.model.mapper;

import com.GP.GP.entities.User;
import com.GP.GP.roles.user.model.dto.StudentDto;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public static StudentDto toDto(User user) {
        StudentDto dto = new StudentDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setNationalId(user.getNationalId());
        dto.setStudentType(user.getStudentType());
        dto.setFaculty(user.getFaculty());
        dto.setLevel(user.getLevel());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setResidenceAddress(user.getResidenceAddress());
        dto.setDetailedAddress(user.getDetailedAddress());
        dto.setPlaceOfBirth(user.getPlaceOfBirth());
        dto.setGender(user.getGender());
        dto.setReligion(user.getReligion());

        dto.setFatherName(user.getFatherName());
        dto.setFatherNationalId(user.getFatherNationalId());
        dto.setFatherOccupation(user.getFatherOccupation());
        dto.setFatherPhoneNumber(user.getFatherPhoneNumber());

        dto.setGuardianName(user.getGuardianName());
        dto.setGuardianNationalId(user.getGuardianNationalId());
        dto.setGuardianPhoneNumber(user.getGuardianPhoneNumber());

        dto.setParentsStatus(user.getParentsStatus());
        dto.setPreviousAcademicYearGpa(user.getPreviousAcademicYearGpa());
        dto.setAnnualGrade(user.getAnnualGrade());
        dto.setSecondaryDivision(user.getSecondaryDivision());
        dto.setTotalGradesHighSchool(user.getTotalGradesHighSchool());

        dto.setStatus(user.getStatus());
        dto.setHousingInPreviousYears(user.getHousingInPreviousYears());
        dto.setFamilyAbroad(user.getFamilyAbroad());
        dto.setSpecialNeeds(user.getSpecialNeeds());

        dto.setPassportNumber(user.getPassportNumber());
        dto.setPassportIssuingAuthority(user.getPassportIssuingAuthority());

        dto.setMedia(user.getMedia());
        dto.setSecurityCheck(user.getSecurityCheck());
        dto.setNote(user.getNote());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}

