package com.GP.GP.roles.user.controller;

import com.GP.GP.roles.user.dto.AdmissionRequestDTO;
import com.GP.GP.roles.user.service.contracts.AdmissionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user/admission-requests")
public class UserController {
    @Autowired
    private AdmissionRequestService admissionRequestService;

    @PostMapping
    public ResponseEntity<AdmissionRequestDTO> applyForAdmission(@RequestBody AdmissionRequestDTO dto) {
        AdmissionRequestDTO createdRequest = admissionRequestService.createAdmissionRequest(dto);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<AdmissionRequestDTO> updateRequest(
//            @PathVariable int id,
//            @RequestBody AdmissionRequestDTO dto) {
//        AdmissionRequestDTO updatedRequest = admissionRequestService.updateAdmissionRequest(id, dto);
//        return ResponseEntity.ok(updatedRequest);
//    }
//
//    @GetMapping("/{id}/status")
//    public ResponseEntity<String> checkStatus(
//            @PathVariable int id,
//            @RequestParam int userId) {
//        String status = admissionRequestService.checkApplicationStatus(id, userId);
//        return ResponseEntity.ok(status);
//    }

}


