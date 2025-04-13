package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.repository.AdmissionRequestRepository;
import com.GP.GP.roles.admin.models.dto.response.AcceptedStudentResponseDTO;
import com.GP.GP.roles.admin.models.mapper.AcceptedStudentMapper;
import com.GP.GP.roles.admin.service.contracts.AcceptedStudentService;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcceptedStudentServiceImpl implements AcceptedStudentService {

    @Autowired
    private AdmissionRequestRepository admissionRequestRepository;

    @Override
    public ResponseEntity<Object> getAcceptedStudents() {
        List<AdmissionRequest> accepted = admissionRequestRepository.findAllByStatus(Enums.AdmissionRequestStatues.ACCEPTED);

        if (accepted.isEmpty()) {
            return new ResponseEntity<>(
                    new BaseResponse(false, "No accepted students found.", null),
                    HttpStatus.NOT_FOUND
            );
        }

        List<AcceptedStudentResponseDTO> responseList = accepted.stream()
                .map(AcceptedStudentMapper::mapToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new BaseResponse(true, "Accepted students retrieved successfully.", responseList),
                HttpStatus.OK
        );
    }
}
