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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentSearchServiceImpl implements StudentSearchService {
    private static final Logger logger = LoggerFactory.getLogger(StudentSearchServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> searchStudentByUsernameAndNationalId(StudentSearchRequestDTO request) {
        String usernamePattern = "%" + request.getUsername() + "%";
        String nationalIdPattern = "%" + request.getNationalId() + "%";

        // Log البحث قبل الاستعلام
        logger.info("Searching for students with username like {} and nationalId like {}", usernamePattern, nationalIdPattern);

        // البحث باستخدام LIKE
        List<User> students = userRepository.findByUsernameLikeAndNationalIdLike(usernamePattern, nationalIdPattern);

        if (students.isEmpty()) {
            // Log عدم وجود أي نتائج
            logger.warn("No students found with the provided criteria");
            return new ResponseEntity<>(new BaseResponse(false, "No students found"), HttpStatus.NOT_FOUND);
        }

        List<StudentSearchResponseDTO> responseDTOs = students.stream()
                .map(StudentSearchMapper::mapStudentToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new BaseResponse(true, "Students retrieved successfully", responseDTOs), HttpStatus.OK);
    }
}
