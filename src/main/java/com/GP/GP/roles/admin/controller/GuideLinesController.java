package com.GP.GP.roles.admin.controller;
import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import com.GP.GP.roles.admin.service.contracts.GuideLineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
public class GuideLinesController {

    @Autowired
    private GuideLineService guideLineService;

    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_ADD_GUIDELINES')")
    @PostMapping(value = "/admin/add-guidelines/{universityId}", consumes = {"multipart/form-data"})
    public ResponseEntity<Object> addGuideLines(
            @PathVariable int universityId,
            @RequestPart("guidelines") String guidelines,
            @RequestPart(value = "media", required = false) MultipartFile media
    ) {
        ApplicationGuidelineAndApprovalDTO request = new ApplicationGuidelineAndApprovalDTO(guidelines, media);
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
    @PutMapping(value = "/admin/update-guidelines", consumes = {"multipart/form-data"})
    public ResponseEntity<Object> updateGuideline(
            @RequestParam int universityId,
            @RequestParam int guidelineId,
            @RequestPart("guidelines") String guidelines,
            @RequestPart(value = "media", required = false) MultipartFile media
    ) {
        ApplicationGuidelineAndApprovalDTO request = new ApplicationGuidelineAndApprovalDTO(guidelines, media);
        return guideLineService.updateGuideline(universityId, guidelineId, request);
    }


}
