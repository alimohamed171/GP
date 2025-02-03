package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface GuideLineService {
    ResponseEntity<Object> addGuideLines(int universityId,ApplicationGuidelineAndApprovalDTO request);

    ResponseEntity<Object> getAllGuidelines(int universityId);

    ResponseEntity<Object> deleteGuideline(int universityId, int guidelineId);

    ResponseEntity<Object> updateGuideline(int universityId, int guidelineId, @Valid ApplicationGuidelineAndApprovalDTO request);
}
