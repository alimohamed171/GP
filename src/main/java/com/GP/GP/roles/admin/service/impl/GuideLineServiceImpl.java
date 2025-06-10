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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class GuideLineServiceImpl implements GuideLineService {

    @Autowired
    private ApplicationGuidelineAndApprovalRepo guidelineRepo;

    @Autowired
    private UniversityService universityService;

    @Override
    public ResponseEntity<Object> addGuideLines(int universityId, ApplicationGuidelineAndApprovalDTO request) {
        University university = universityService.findUniversityById(universityId);

        if (university == null) {
            return new ResponseEntity<>(new BaseResponse(false, "No university found", null), HttpStatus.NOT_FOUND);
        }

        String filePath = null;
        if (request.getMedia() != null && !request.getMedia().isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "guidelines";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + request.getMedia().getOriginalFilename();
                File dest = new File(directory, fileName);
                request.getMedia().transferTo(dest);
                filePath = fileName;

            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(new BaseResponse(false, "Error uploading file", null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        ApplicationGuidelineAndApproval guideline = ApplicationGuidelineAndApproval.builder()
                .guidelines(request.getGuidelines())
                .media(filePath)
                .university(university)
                .build();

        guideline = guidelineRepo.save(guideline);
        ApplicationGuidelineAndApprovalResponseDTO responseDto = ApplicationGuidelineAndApprovalResponseDTO.mapToResponseDTO(guideline);

        return new ResponseEntity<>(new BaseResponse(true, "Guidelines added successfully.", responseDto), HttpStatus.OK);
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

    @Override
    public ResponseEntity<Object> updateGuideline(int universityId, int guidelineId, ApplicationGuidelineAndApprovalDTO request) {
        Optional<ApplicationGuidelineAndApproval> guidelineOptional = guidelineRepo.findById(guidelineId);

        if (guidelineOptional.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Guideline not found", null), HttpStatus.NOT_FOUND);
        }

        ApplicationGuidelineAndApproval guideline = guidelineOptional.get();

        guideline.setGuidelines(request.getGuidelines());

        if (request.getMedia() != null && !request.getMedia().isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "guidelines";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + request.getMedia().getOriginalFilename();
                File dest = new File(directory, fileName);
                request.getMedia().transferTo(dest);
                guideline.setMedia(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(new BaseResponse(false, "Error uploading file", null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        guideline = guidelineRepo.save(guideline);
        ApplicationGuidelineAndApprovalResponseDTO responseDTO = ApplicationGuidelineAndApprovalResponseDTO.mapToResponseDTO(guideline);
        return new ResponseEntity<>(new BaseResponse(true, "Guideline updated successfully.", responseDTO), HttpStatus.OK);
    }

}
