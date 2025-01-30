package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.roles.admin.models.dto.request.UniversityDTO;
import com.GP.GP.entities.University;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.roles.admin.models.mapper.AdminMapper;
import com.GP.GP.repository.UniversityRepository;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityRepository repo;

    @Override
    public University findUniversityById(int id) {
        return repo.findById(id)
                .orElse(null);
    }

    @Override
    public ResponseEntity<Object> addUniversity(UniversityDTO request) {
        University university = AdminMapper.toEntity(request);
        university = repo.save(university);
        UniversityResponseDTO responseDTO = UniversityResponseDTO.mapToResponseDTO(university);
        BaseResponse response = new BaseResponse(true, "University added successfully.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

