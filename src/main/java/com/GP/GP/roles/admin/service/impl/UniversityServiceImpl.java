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

import java.util.List;
import java.util.stream.Collectors;

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
        University university = AdminMapper.toUniversityEntity(request);
        university = repo.save(university);
        UniversityResponseDTO responseDTO = UniversityResponseDTO.mapToResponseDTO(university);
        BaseResponse response = new BaseResponse(true, "University added successfully.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteUniversityById(int id) {
        University university = findUniversityById(id);
        if (university == null) {
            BaseResponse response = new BaseResponse(false, "University with ID " + id + " not found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        repo.delete(university);
        BaseResponse response = new BaseResponse(true, "University deleted successfully.", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateUniversity(int id, UniversityDTO request) {
       University university = findUniversityById(id);
       if (university == null){
           BaseResponse response = new BaseResponse(false, "University with ID " + id + " not found.", null);
           return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
       }

        if (request.getName() != null) {
            university.setName(request.getName());
        }
       university = repo.save(university);
       UniversityResponseDTO responseDTO = UniversityResponseDTO.mapToResponseDTO(university);
       BaseResponse response = new BaseResponse(true, "University updated successfully.", responseDTO);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllUniversity() {
        List<University> universities = repo.findAll();
        List<UniversityResponseDTO> universityResponseDTOs = universities.stream()
                .map(UniversityResponseDTO::mapToResponseDTO)
                .collect(Collectors.toList());
        BaseResponse response = new BaseResponse(true, "All universities retrieved successfully.", universityResponseDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

