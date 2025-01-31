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
    public ResponseEntity<Object> applyForAdmission(@RequestBody AdmissionRequestDTO dto) {
        return admissionRequestService.createAdmissionRequest(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRequest(@PathVariable int id, @RequestBody AdmissionRequestDTO dto) {
        return admissionRequestService.updateAdmissionRequest(id, dto);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Object> checkStatus(@PathVariable int id, @RequestParam int userId) {
        return admissionRequestService.checkApplicationStatus(id, userId);
    }

}


