package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.University;
import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.request.AdmissionRequestExportDtO;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.security.Role;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Component
public class AdmissionRequestExportMapper {
    public AdmissionRequestExportDtO toDto(User request, University university) {
        if (request == null) {
            return null;
        }
        AdmissionRequestExportDtO.AdmissionRequestExportDtOBuilder builder = AdmissionRequestExportDtO.builder();
        if (request.getRole() != Role.ADMIN) {
            builder

                    .fullName(request.getFirstName() + " " + request.getLastName())
                    .email(request.getUsername())
                    .universityName(university.getName())
                    .nationalId(request.getNationalId())
                    .mobileNumber(request.getMobileNumber())
                    .faculty(request.getFaculty())
                    .level(request.getLevel())
                    .dateOfBirth(request.getDateOfBirth())
                    .studentType(request.getStudentType())
                    .residenceAddress(request.getResidenceAddress())
                    .detailedAddress(request.getDetailedAddress())
                    .placeOfBirth(request.getPlaceOfBirth())
                    .gender(request.getGender())
                    .religion(request.getReligion())
                    .fatherName(request.getFatherName())
                    .fatherNationalId(request.getFatherNationalId())
                    .fatherOccupation(request.getFatherOccupation())
                    .fatherPhoneNumber(request.getFatherPhoneNumber())
                    .guardianName(request.getGuardianName())
                    .guardianNationalId(request.getGuardianNationalId())
                    .guardianPhoneNumber(request.getGuardianPhoneNumber())
                    .previousAcademicYearGpa(request.getPreviousAcademicYearGpa())
                    .status(request.getStatus())
                    .housingInPreviousYears(request.getHousingInPreviousYears())
                    .familyAbroad(request.getFamilyAbroad())
                    .specialNeeds(request.getSpecialNeeds())
                    .secondaryDivision(request.getSecondaryDivision())
                    .totalGradesHighSchool(request.getTotalGradesHighSchool())
                    .passportNumber(request.getPassportNumber())
                    .passportIssuingAuthority(request.getPassportIssuingAuthority())
                    .securityCheck(request.getSecurityCheck())
                    .build();
        }
       return builder.build();
    }
    public  List<AdmissionRequestExportDtO> toDtoList(List<User> requests) {
        return requests.stream()
                .map(user -> toDto(user, user.getUniversity()))
                .collect(Collectors.toList());
    }
}
