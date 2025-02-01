package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import com.GP.GP.entities.ApplicationGuidelineAndApproval;
import com.GP.GP.entities.University;
import com.GP.GP.roles.admin.models.dto.response.ApplicationGuidelineAndApprovalResponseDTO;
import com.GP.GP.roles.admin.models.mapper.AdminMapper;
import com.GP.GP.repository.ApplicationGuidelineAndApprovalRepo;
import com.GP.GP.roles.admin.service.contracts.GuideLineService;
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
public class GuideLineServiceImpl implements GuideLineService {

    @Autowired
    private ApplicationGuidelineAndApprovalRepo guidelineRepo;

    @Autowired
    private UniversityService universityService;

    @Override
    public ResponseEntity<Object> addGuideLines(int universityId,@Valid ApplicationGuidelineAndApprovalDTO request) {

        University university = universityService.findUniversityById(universityId);
        ApplicationGuidelineAndApproval guidelineEntity = AdminMapper.toApplicationGuidelineAndApprovalEntity(request, university);

        if (university == null){
            BaseResponse response = new BaseResponse(false, "No university found with ID "+ universityId, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        guidelineEntity = guidelineRepo.save(guidelineEntity);
        ApplicationGuidelineAndApprovalResponseDTO responseDto = ApplicationGuidelineAndApprovalResponseDTO.mapToResponseDTO(guidelineEntity);
        BaseResponse response = new BaseResponse(true, "Guidelines added successfully.", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllGuidelines(int universityId) {
        University university = universityService.findUniversityById(universityId);

        if (university == null) {
            BaseResponse response = new BaseResponse(false, "No university found ", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<ApplicationGuidelineAndApproval> guidelines = guidelineRepo.findByUniversity(university);

        if (guidelines.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "No guidelines found for university", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<ApplicationGuidelineAndApprovalResponseDTO> responseDTOs = guidelines.stream()
                .map(ApplicationGuidelineAndApprovalResponseDTO::mapToResponseDTO)
                .toList();

        BaseResponse response = new BaseResponse(true, "Guidelines retrieved successfully.", responseDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteGuideline(int universityId, int guidelineId) {
        University university = universityService.findUniversityById(universityId);

        if (university == null) {
            BaseResponse response = new BaseResponse(false, "No university found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<ApplicationGuidelineAndApproval> guideline = guidelineRepo.findById(guidelineId);

        if (guideline.isEmpty() || guideline.get().getUniversity().getId() != universityId) {
            BaseResponse response = new BaseResponse(false, "No guideline found with this university", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        guidelineRepo.delete(guideline.get());
        guidelineRepo.flush();

        BaseResponse response = new BaseResponse(true, "Guideline deleted successfully.", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
