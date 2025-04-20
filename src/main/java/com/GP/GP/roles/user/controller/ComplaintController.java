package com.GP.GP.roles.user.controller;

import com.GP.GP.entities.User;
import com.GP.GP.roles.Auth.service.AuthenticationService;
import com.GP.GP.roles.user.model.request.ComplaintRequestDTO;
import com.GP.GP.roles.user.service.contracts.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController

public class ComplaintController {
    @Autowired
   private ComplaintService complaintService;

    // Create a new complaint (User)
    @PostMapping("/user/make-complaint/{userId}")
    public ResponseEntity<Object> createComplaint(
            @PathVariable int userId,
            @Valid @RequestBody ComplaintRequestDTO request) {
        return complaintService.createComplaint(request, userId);
    }

    // Get all complaints (Admin)
    @GetMapping("/admin/view/get-all-complaints")
    public ResponseEntity<Object> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    // Get a complaint by ID (Admin)
    @GetMapping("/admin/view/get-complaint/{id}")
    public ResponseEntity<Object> getComplaintById(@PathVariable int id) {
        return complaintService.getComplaintById(id);
    }

    // Update a complaint by ID (User)
    @PutMapping("/user/update-complaint/{id}")
    public ResponseEntity<Object> updateComplaint(
            @PathVariable int id,
            @Valid @RequestBody ComplaintRequestDTO request) {
        return complaintService.updateComplaint(id, request);
    }

    // Delete a complaint by ID (Admin)
    @DeleteMapping("/admin/delete-complaint/{id}")
    public ResponseEntity<Object> deleteComplaint(@PathVariable int id) {
        return complaintService.deleteComplaint(id);
    }

    // Get all complaints by a specific user (Admin)
    @GetMapping("/admin/view/get-user-complaints/{userId}")
    public ResponseEntity<Object> getComplaintsByUser(@PathVariable int userId) {
        return complaintService.getComplaintsByUser(userId);
    }
    // Get all complaints by a specific user (User)
    @GetMapping("/user/my-complaints/{userId}")
    public ResponseEntity<Object> getAllUserComplaints(@PathVariable int userId) {
        return complaintService.getComplaintsByUser(userId);
    }
}
