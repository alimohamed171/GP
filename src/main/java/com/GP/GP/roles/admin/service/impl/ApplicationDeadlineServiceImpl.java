package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.ApplicationDeadline;
import com.GP.GP.entities.University;
import com.GP.GP.repository.ApplicationDeadlineRepository;
import com.GP.GP.roles.admin.models.dto.request.ApplicationDeadlineDTO;
import com.GP.GP.roles.admin.models.dto.response.ApplicationDeadlineResponseDTO;
import com.GP.GP.roles.admin.models.mapper.ApplicationDeadlineMapper;
import com.GP.GP.roles.admin.service.contracts.ApplicationDeadlineService;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import com.GP.GP.utill.base.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ApplicationDeadlineServiceImpl implements ApplicationDeadlineService {

    @Autowired
    private ApplicationDeadlineRepository repo;

    @Autowired
    private UniversityService universityService;
    @Override
    public ResponseEntity<Object> addApplicationDeadline(int universityId,@Valid ApplicationDeadlineDTO dto) {
        University university = universityService.findUniversityById(universityId);
        if (university == null){
            BaseResponse response = new BaseResponse(false, "University not found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApplicationDeadline applicationDeadline = ApplicationDeadlineMapper.toApplicationDeadlineEntity(dto, university);
        applicationDeadline = repo.save(applicationDeadline);
        ApplicationDeadlineResponseDTO responseDTO = ApplicationDeadlineResponseDTO.mapToResponseDTO(applicationDeadline);

        BaseResponse response = new BaseResponse(true, "Application deadline added successfully.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
