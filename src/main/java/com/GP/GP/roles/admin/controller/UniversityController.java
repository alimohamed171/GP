package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.UniversityDTO;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("")
public class UniversityController {


    @Autowired
    private UniversityService universityService;
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_ADD_UNIVERSITY')")
    @PostMapping("/admin/edit/university")
    public ResponseEntity<Object> addUniversity(@Valid @RequestBody UniversityDTO request) {
        return universityService.addUniversity(request);
    }
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_DELETE_UNIVERSITY')")
    @DeleteMapping("/admin/deleteUniversity/{id}")
    public ResponseEntity<Object> deleteUniversityById(@PathVariable int id) {
        return universityService.deleteUniversityById(id);
    }

    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_UPDATE_UNIVERSITY')")
    @PutMapping("/admin/edit/update_university/{id}")
    public ResponseEntity<Object> updateUniversity(
            @PathVariable int id,
            @Valid @RequestBody UniversityDTO request) {
        return universityService.updateUniversity(id, request);
    }
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_VIEW_UNIVERSITY')")
    @GetMapping("/public/getAllUniversity")
    public ResponseEntity<Object> getAllUniversity(){
        return universityService.getAllUniversity();
    }
}
