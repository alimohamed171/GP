package com.GP.GP.roles.admin.controller;
import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import com.GP.GP.roles.admin.service.contracts.GuideLineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class GuideLinesController {

    @Autowired
    private GuideLineService guideLineService;

    @PostMapping("/admin/guidelines/{universityId}")
    public ResponseEntity<Object> addGuideLines(@PathVariable int universityId, @Valid @RequestBody ApplicationGuidelineAndApprovalDTO request) {
        return guideLineService.addGuideLines(universityId, request);
    }
}
