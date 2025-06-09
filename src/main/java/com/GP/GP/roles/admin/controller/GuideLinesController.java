package com.GP.GP.roles.admin.controller;
import com.GP.GP.roles.admin.models.dto.request.ApplicationGuidelineAndApprovalDTO;
import com.GP.GP.roles.admin.service.contracts.GuideLineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
public class GuideLinesController {

    @Autowired
    private GuideLineService guideLineService;

    @PostMapping(value = "/admin/edit/add-guidelines/{universityId}", consumes = {"multipart/form-data"})
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

    @DeleteMapping("/admin/delete-guidelines")
    public ResponseEntity<Object> deleteGuideline(
            @RequestParam int universityId,
            @RequestParam int guidelineId) {
        return guideLineService.deleteGuideline(universityId, guidelineId);
    }

    @PutMapping("/admin/edit/update-guidelines")
    public ResponseEntity<Object> updateGuideline(
            @RequestParam int universityId,
            @RequestParam int guidelineId,
            @Valid @RequestBody ApplicationGuidelineAndApprovalDTO request) {
        return guideLineService.updateGuideline(universityId, guidelineId, request);
    }
}
