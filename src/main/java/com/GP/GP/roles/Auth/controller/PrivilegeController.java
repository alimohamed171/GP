package com.GP.GP.roles.Auth.controller;

import com.GP.GP.roles.Auth.models.request.PrivilegesAssignmentRequestDTO;
import com.GP.GP.roles.Auth.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
public class PrivilegeController {
    @Autowired
    PrivilegeService privilegeService;
    @GetMapping("/privileges")
    public ResponseEntity<Object> getPrivileges() {
        return privilegeService.getAllPrivileges();
    }
    @GetMapping("/privileges/id/{id}")
    public ResponseEntity<Object> getPrivilegeById(@PathVariable Integer id) {
        return privilegeService.getPrivilegeById(id);
    }
    @GetMapping("/privileges/user-privileges/{userId}")
    public ResponseEntity<Object> getPrivilegesByUserId(@PathVariable Integer userId) {
        return privilegeService.getPrivilegesByUserId(userId);
    }
    @PostMapping("/privileges/assign")
    public ResponseEntity<Object> assignPrivilegesToUser(@RequestBody PrivilegesAssignmentRequestDTO request) {
        return privilegeService.assignPrivilegeToUser(request);
    }
    @PutMapping("/privileges/revoke")
    public ResponseEntity<Object> revokePrivilegesFromUser(@RequestBody PrivilegesAssignmentRequestDTO request) {
        return privilegeService.revokePrivilegesFromUser(request);
    }
    @DeleteMapping("/privileges/revoke-all/{userId}")
    public ResponseEntity<Object> revokeAllPrivilegesFromUser(@PathVariable Integer userId) {
        return privilegeService.revokeAllPrivilegesFromUser(userId);
    }


}
