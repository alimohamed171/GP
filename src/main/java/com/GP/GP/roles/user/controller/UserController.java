package com.GP.GP.roles.user.controller;

import com.GP.GP.roles.user.model.dto.AdmissionRequestDTO;
import com.GP.GP.roles.user.model.request.UpdateUserRequestDTO;
import com.GP.GP.roles.user.service.contracts.AdmissionRequestService;
import com.GP.GP.utill.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private AdmissionRequestService admissionRequestService;

    @PostMapping("/user/admission-requests")
    public ResponseEntity<Object> applyForAdmission(@RequestBody AdmissionRequestDTO dto) {
        return admissionRequestService.createAdmissionRequest(dto);
    }

    @PutMapping("/user/admission-requests/{id}")
    public ResponseEntity<Object> updateRequest(@PathVariable int id, @RequestBody UpdateUserRequestDTO dto) {
        return admissionRequestService.updateUser(id, dto);
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
    @PutMapping("/admin/admission-requests/{id}/status")
    public ResponseEntity<Object> updateAdmissionRequestStatus(@PathVariable int id, @RequestParam Enums.AdmissionRequestStatues status) {
        return admissionRequestService.updateAdmissionRequestStatues(id, status);
    }
    @GetMapping("/user/admission-requests/nid/{nationalId}/status")
    public ResponseEntity<Object> checkApplicationStatus(@PathVariable String nationalId) {
        return admissionRequestService.getApplicationStatusByNID(nationalId);
    }



}


