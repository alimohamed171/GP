package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import org.springframework.http.ResponseEntity;

public interface GuideLineService {
    ResponseEntity<Object> addGuideLines(int universityId,ApplicationGuidelineAndApprovalDTO request);
}
