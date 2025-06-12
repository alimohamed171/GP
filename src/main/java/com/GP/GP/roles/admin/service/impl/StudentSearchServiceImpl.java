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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentSearchServiceImpl implements StudentSearchService {
    private static final Logger logger = LoggerFactory.getLogger(StudentSearchServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> searchStudentByUsernameAndNationalId(StudentSearchRequestDTO request, int offset, int limit) {
        String usernamePattern = "%" + request.getUsername() + "%";
        String nationalIdPattern = "%" + request.getNationalId() + "%";

        Pageable pageable = PageRequest.of(offset, limit);

        Page<User> studentsPage = userRepository
                .findByUsernameLikeAndNationalIdLike(usernamePattern, nationalIdPattern, pageable);

        if (studentsPage.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No students found"), HttpStatus.NOT_FOUND);
        }

        List<StudentSearchResponseDTO> responseDTOs = studentsPage.stream()
                .map(StudentSearchMapper::mapStudentToDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("meta", createPageMeta(studentsPage));
        response.put("data", responseDTOs);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Map<String, Object> createPageMeta(Page<?> page) {
        Map<String, Object> meta = new HashMap<>();
        meta.put("pageNumber", page.getNumber());
        meta.put("pageSize", page.getSize());
        meta.put("offset", page.getPageable().getOffset());
        meta.put("totalElements", page.getTotalElements());
        meta.put("totalPages", page.getTotalPages());
        meta.put("isLast", page.isLast());
        meta.put("isFirst", page.isFirst());
        return meta;
    }


}
