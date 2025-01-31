package com.GP.GP.roles.user.controller;

import com.GP.GP.roles.user.dto.AdmissionRequestDTO;
import com.GP.GP.roles.user.service.contracts.AdmissionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdmissionRequestController {
    @Autowired
    private AdmissionRequestService admissionRequestService;

    @PostMapping("/user/admission-requests")
    public ResponseEntity<Object> applyForAdmission(@RequestBody AdmissionRequestDTO dto) {
        return admissionRequestService.createAdmissionRequest(dto);
    }

    @PutMapping("/user/admission-requests/{id}")
    public ResponseEntity<Object> updateRequest(@PathVariable int id, @RequestBody AdmissionRequestDTO dto) {
        return admissionRequestService.updateAdmissionRequest(id, dto);
    }

    @GetMapping("/user/admission-requests/{id}/status")
    public ResponseEntity<Object> checkStatus(@PathVariable int id, @RequestParam int userId) {
        return admissionRequestService.checkApplicationStatus(id, userId);
    }

    // get all admission -> admin
    @GetMapping("/admin/admission-requests")
    public ResponseEntity<Object> getAllAdmissionRequests() {
        return admissionRequestService.getAllAdmissionRequests();
    }

    //get admission by user Id -> user(get admission request to update it same to )
    @GetMapping("/user/admission-requests/{userId}")
    public ResponseEntity<Object> getAdmissionRequestByUserId(@PathVariable int userId) {
        return admissionRequestService.getAdmissionRequestByUserId(userId);
    }

    // update statues ->admin (admissionId, enum.Admission status )


}


