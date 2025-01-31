package com.GP.GP.roles.user.service.impl;

import com.GP.GP.entities.University;
import com.GP.GP.repository.AdmissionRequestRepository;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import com.GP.GP.roles.user.service.contracts.AdmissionRequestService;
import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.entities.User;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.user.dto.AdmissionRequestDTO;
import com.GP.GP.roles.user.exception.InvalidOperationException;
import com.GP.GP.roles.user.exception.ResourceNotFoundException;
import com.GP.GP.roles.user.mapper.AdmissionRequestMapper;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AdmissionRequestImpl implements AdmissionRequestService {
    private static final Logger log = LoggerFactory.getLogger(AdmissionRequestImpl.class);
    @Autowired
    private AdmissionRequestRepository admissionRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UniversityService universityService;

    @Override
    public ResponseEntity<Object> createAdmissionRequest(AdmissionRequestDTO admissionRequestDTO) {
        User user = userRepository.findById(admissionRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ${admissionRequestDTO.getUserId()}"));
        University university = universityService.findUniversityById(admissionRequestDTO.getUniversityId());
        AdmissionRequest admissionRequest;

        admissionRequest = AdmissionRequestMapper.toEntity(admissionRequestDTO, user,university);


        admissionRequest.setStatus(Enums.AdmissionRequestStatues.UNDER_REVIEW);
        admissionRequest.setCreatedAt(LocalDateTime.now());

        AdmissionRequest savedRequest = admissionRequestRepository.save(admissionRequest);

        AdmissionRequestDTO dto = AdmissionRequestMapper.toDTO(savedRequest);
        BaseResponse response = new BaseResponse(true, "Admission request created successfully", dto);


        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateAdmissionRequest(int id, AdmissionRequestDTO admissionRequestDTO) {
        AdmissionRequest existingRequest = admissionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admission request not found"));

        if (existingRequest.getStatus() != Enums.AdmissionRequestStatues.UNDER_REVIEW) {
            throw new InvalidOperationException("Cannot update request after it has been processed");
        }
        //I don't know what else could be updated :(
        existingRequest.setStudentType(admissionRequestDTO.getStudentType());
        existingRequest.setNationalId(admissionRequestDTO.getNationalId());
        existingRequest.setName(admissionRequestDTO.getName());
        existingRequest.setDateOfBirth(admissionRequestDTO.getDateOfBirth());
        existingRequest.setPlaceOfBirth(admissionRequestDTO.getPlaceOfBirth());
        existingRequest.setGender(admissionRequestDTO.getGender());
        existingRequest.setReligion(admissionRequestDTO.getReligion());
        existingRequest.setResidenceAddress(admissionRequestDTO.getResidenceAddress());
        existingRequest.setDetailedAddress(admissionRequestDTO.getDetailedAddress());
        existingRequest.setEmail(admissionRequestDTO.getEmail());
        existingRequest.setMobileNumber(admissionRequestDTO.getMobileNumber());
        existingRequest.setFatherName(admissionRequestDTO.getFatherName());
        existingRequest.setFatherNationalId(admissionRequestDTO.getFatherNationalId());
        existingRequest.setFatherOccupation(admissionRequestDTO.getFatherOccupation());
        existingRequest.setFatherPhoneNumber(admissionRequestDTO.getFatherPhoneNumber());
        existingRequest.setGuardianName(admissionRequestDTO.getGuardianName());
        existingRequest.setGuardianNationalId(admissionRequestDTO.getGuardianNationalId());
        existingRequest.setGuardianPhoneNumber(admissionRequestDTO.getGuardianPhoneNumber());
        existingRequest.setParentsStatus(admissionRequestDTO.getParentsStatus());
        existingRequest.setPreviousAcademicYearGpa(admissionRequestDTO.getPreviousAcademicYearGpa());
        existingRequest.setHousingInPreviousYears(admissionRequestDTO.getHousingInPreviousYears());
        existingRequest.setFamilyAbroad(admissionRequestDTO.getFamilyAbroad());
        existingRequest.setSpecialNeeds(admissionRequestDTO.getSpecialNeeds());
        existingRequest.setSecondaryDivision(admissionRequestDTO.getSecondaryDivision());
        existingRequest.setTotalGradesHighSchool(admissionRequestDTO.getTotalGradesHighSchool());
        existingRequest.setPassportNumber(admissionRequestDTO.getPassportNumber());
        existingRequest.setPassportIssuingAuthority(admissionRequestDTO.getPassportIssuingAuthority());
        existingRequest.setDate(admissionRequestDTO.getDate());

        existingRequest.setUpdatedAt(LocalDateTime.now());

        AdmissionRequest updatedRequest = admissionRequestRepository.save(existingRequest);
        AdmissionRequestDTO dto = AdmissionRequestMapper.toDTO(updatedRequest);
        BaseResponse response =new BaseResponse(true, "Admission request updated successfully", dto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> checkApplicationStatus(int id, int userId) {
        AdmissionRequest request = admissionRequestRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission request not found"));
        String admissionRequestStatues = request.getStatus().name();
        BaseResponse response = new BaseResponse(true, "Application status retrieved successfully", admissionRequestStatues);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
