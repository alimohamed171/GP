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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AdmissionRequestDTO createAdmissionRequest(AdmissionRequestDTO admissionRequestDTO) {
        User user = userRepository.findById(admissionRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ${admissionRequestDTO.getUserId()}"));
        University university = universityService.findUniversityById(admissionRequestDTO.getUniversityId());
        AdmissionRequest admissionRequest;

        admissionRequest = AdmissionRequestMapper.toEntity(admissionRequestDTO, user,university);


        admissionRequest.setStatus(Enums.AdmissionRequestStatues.UNDER_REVIEW);
        admissionRequest.setCreatedAt(LocalDateTime.now());

        AdmissionRequest savedRequest = admissionRequestRepository.save(admissionRequest);

        return AdmissionRequestMapper.toDTO(savedRequest);
    }

    @Override
    public AdmissionRequestDTO updateAdmissionRequest(int id, AdmissionRequestDTO admissionRequestDTO) {
        AdmissionRequest existingRequest = admissionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admission request not found"));

        if (existingRequest.getStatus() != Enums.AdmissionRequestStatues.UNDER_REVIEW) {
            throw new InvalidOperationException("Cannot update request after it has been processed");
        }
        //I don't know what else could be updated :(
        existingRequest.setHousingType(admissionRequestDTO.getHousingType());
        existingRequest.setUpdatedAt(LocalDateTime.now());
        AdmissionRequest updatedRequest = admissionRequestRepository.save(existingRequest);
        return AdmissionRequestMapper.toDTO(updatedRequest);
    }

    @Override
    public String checkApplicationStatus(int id, int userId) {
        AdmissionRequest request = admissionRequestRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission request not found"));
        return request.getStatus().name();
    }


}
