package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.University;
import com.GP.GP.entities.User;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.admin.models.dto.request.StudentSearchRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.StudentSearchResponseDTO;
import com.GP.GP.roles.admin.models.mapper.StudentSearchMapper;
import com.GP.GP.roles.admin.service.contracts.StudentSearchService;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentSearchServiceImpl implements StudentSearchService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> searchStudentByUsernameAndNationalId(StudentSearchRequestDTO request) {
        Optional<User> student = userRepository.findByUsernameAndNationalId(request.getUsername(), request.getNationalId());

        if (student.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Student not found"), HttpStatus.NOT_FOUND);
        }

        StudentSearchResponseDTO dto = StudentSearchMapper.mapStudentToDTO(student.get());
        return new ResponseEntity<>(new BaseResponse(true, "Student retrieved successfully", dto), HttpStatus.OK);
    }
}
