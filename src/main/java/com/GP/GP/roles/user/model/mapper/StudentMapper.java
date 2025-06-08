package com.GP.GP.roles.user.model.mapper;

import com.GP.GP.entities.Room;
import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.response.BuildingResponseForUserDTO;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.roles.admin.models.dto.response.RoomResponseForUserDTO;
import com.GP.GP.roles.user.model.dto.StudentDto;
import com.GP.GP.roles.user.model.response.UpdatedUserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public static StudentDto toDto(User user) {
        Room room = user.getRoom();
        return StudentDto.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .university(new UniversityResponseDTO(user.getUniversity().getId(), user.getUniversity().getName()))
                .nationalId(user.getNationalId())
                .mobileNumber(user.getMobileNumber())
                .faculty(user.getFaculty())
                .level(user.getLevel())
                .penaltiesCount(user.getPenalties().size())
                .dateOfBirth(user.getDateOfBirth())
                .room(
                        room != null
                                ? new RoomResponseForUserDTO(
                                room.getId(),
                                room.getRoomNumber(),
                                room.getCapacity(),
                                room.getCurrentOccupancy(),
                                room.getType(),
                                room.getStatus(),
                                room.getBuilding() != null
                                        ? new BuildingResponseForUserDTO(
                                        room.getBuilding().getId(),
                                        room.getBuilding().getName(),
                                        room.getBuilding().getType()
                                ) : null
                        ) : null
                )
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
                .securityCheckStatues(user.getSecurityCheck())
                .securityCheckNotes(user.getSecurityCheckNotes())
                .AdmissionRequestStatusNotes(user.getAdmissionRequestStatusNotes())
                .studentCode(user.getStudentCode())
                .guardianRelationship(user.getGuardianRelationship())
                .phoneNumber(user.getPhoneNumber())
                .houseTypeName(user.getHouseTypeName())
                .annualGrade(user.getAnnualGrade())
                .wantFood(user.getWantFood())
                .build();
    }
}
