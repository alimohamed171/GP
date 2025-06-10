package com.GP.GP.roles.admin.controller;
import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import com.GP.GP.roles.admin.service.contracts.GuideLineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class GuideLinesController {

    @Autowired
    private GuideLineService guideLineService;

    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_ADD_GUIDELINES')")
    @PostMapping("/admin/edit/add-guidelines/{universityId}")
    public ResponseEntity<Object> addGuideLines(@PathVariable int universityId, @Valid @RequestBody ApplicationGuidelineAndApprovalDTO request) {
        return guideLineService.addGuideLines(universityId, request);
    }

    @GetMapping("/public/get-guidelines/{universityId}")
    public ResponseEntity<Object> getAllGuidelines(@PathVariable int universityId) {
        return guideLineService.getAllGuidelines(universityId);
    }
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_DELETE_GUIDELINES')")
    @DeleteMapping("/admin/delete-guidelines")
    public ResponseEntity<Object> deleteGuideline(
            @RequestParam int universityId,
            @RequestParam int guidelineId) {
        return guideLineService.deleteGuideline(universityId, guidelineId);
    }
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_UPDATE_GUIDELINES')")
    @PutMapping("/admin/edit/update-guidelines")
    public ResponseEntity<Object> updateGuideline(
            @RequestParam int universityId,
            @RequestParam int guidelineId,
            @Valid @RequestBody ApplicationGuidelineAndApprovalDTO request) {
        return guideLineService.updateGuideline(universityId, guidelineId, request);
    }
}
