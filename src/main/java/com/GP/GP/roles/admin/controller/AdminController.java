package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import com.GP.GP.roles.admin.models.dto.request.UniversityDTO;
import com.GP.GP.roles.admin.service.contracts.GuideLineService;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private GuideLineService guideLineService;

    @Autowired
    private UniversityService universityService;

    @PostMapping("guidelines/{universityId}")
    public ResponseEntity<Object> addGuideLines(@PathVariable int universityId, @Valid @RequestBody ApplicationGuidelineAndApprovalDTO request) {
        return guideLineService.addGuideLines(universityId, request);
    }

    @PostMapping("university")
    public ResponseEntity<Object> addUniversity(@Valid @RequestBody UniversityDTO request) {
        return universityService.addUniversity(request);
    }

    @DeleteMapping("deleteUniversity/{id}")
    public ResponseEntity<Object> deleteUniversityById(@PathVariable int id) {
        return universityService.deleteUniversityById(id);
    }

    @PutMapping("university/{id}")
    public ResponseEntity<Object> updateUniversity(
            @PathVariable int id,
            @Valid @RequestBody UniversityDTO request) {
        return universityService.updateUniversity(id, request);
    }

}
