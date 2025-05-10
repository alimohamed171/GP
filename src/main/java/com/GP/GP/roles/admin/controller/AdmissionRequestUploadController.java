package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.service.contracts.AdmissionRequestUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
public class AdmissionRequestUploadController {
    @Autowired
    private AdmissionRequestUploadService admissionRequestUploadService;
    @PostMapping("/admin/validate-excel")
    public ResponseEntity<Object> validateExcel(@RequestParam("file") MultipartFile file) {
        return admissionRequestUploadService.isValidExcelFile(file);
    }
    @PostMapping("/admin/upload-admission-request")
    public ResponseEntity<Object> uploadAdmissionRequest(@RequestParam("file") MultipartFile file) {
        return admissionRequestUploadService.uploadAdmissionRequestSecurityCheck(file);
    }
    @PostMapping("/admin/upload-admission-request-status")
    public ResponseEntity<Object> uploadAdmissionRequestStatus(@RequestParam("file") MultipartFile file) {
        return admissionRequestUploadService.uploadAdmissionRequestStatusesFromExcel(file);
    }

}
