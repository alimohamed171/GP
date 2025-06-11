package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.service.contracts.AdmissionAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AdmissionAnalysisController {
     @Autowired
    private AdmissionAnalysisService admissionAnalysisService;
     @GetMapping("/admin/view/admission-analysis")
     public ResponseEntity<Object> getAdmissionAnalysis() {
         return admissionAnalysisService.analysisAdmissionRequest();
     }
}
