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

import java.util.List;
import java.util.Optional;

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

    @Override
    public ResponseEntity<Object> deleteApplicationDeadlineById(int deadlineId, int universityId) {
        Optional<ApplicationDeadline> applicationDeadline = repo.findByIdAndUniversityId(deadlineId, universityId);

        if (applicationDeadline.isEmpty()) {
            BaseResponse baseResponse = new BaseResponse(false, "Application deadline not found for this university.", null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }

        repo.delete(applicationDeadline.get());
        BaseResponse baseResponse = new BaseResponse(true, "Application deadline deleted successfully.", null);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllAppDeadlinesByUniversityId(int universityId) {
        List<ApplicationDeadline> deadlines = repo.findAllByUniversityId(universityId);

        if (deadlines.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "No deadlines found for this university.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<ApplicationDeadlineResponseDTO> responseDTOs = deadlines.stream()
                .map(ApplicationDeadlineResponseDTO::mapToResponseDTO)
                .toList();

        BaseResponse response = new BaseResponse(true, "Deadlines retrieved successfully.", responseDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateApplicationDeadline(int deadlineId, int universityId, ApplicationDeadlineDTO dto) {
        Optional<ApplicationDeadline> optionalDeadline = repo.findByIdAndUniversityId(deadlineId, universityId);

        if (optionalDeadline.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "Application deadline not found for this university.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApplicationDeadline applicationDeadline = optionalDeadline.get();

        ApplicationDeadlineMapper.updateApplicationDeadlineEntity(applicationDeadline, dto);

        repo.save(applicationDeadline);

        ApplicationDeadlineResponseDTO responseDTO = ApplicationDeadlineResponseDTO.mapToResponseDTO(applicationDeadline);

        BaseResponse response = new BaseResponse(true, "Application deadline updated successfully.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
