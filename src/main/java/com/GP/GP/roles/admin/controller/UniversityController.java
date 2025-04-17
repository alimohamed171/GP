package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.UniversityDTO;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class UniversityController {


    @Autowired
    private UniversityService universityService;

    @PostMapping("/admin/edit/university")
    public ResponseEntity<Object> addUniversity(@Valid @RequestBody UniversityDTO request) {
        return universityService.addUniversity(request);
    }

    @DeleteMapping("/admin/deleteUniversity/{id}")
    public ResponseEntity<Object> deleteUniversityById(@PathVariable int id) {
        return universityService.deleteUniversityById(id);
    }

    @PutMapping("/admin/edit/update_university/{id}")
    public ResponseEntity<Object> updateUniversity(
            @PathVariable int id,
            @Valid @RequestBody UniversityDTO request) {
        return universityService.updateUniversity(id, request);
    }

    @GetMapping("/public/getAllUniversity")
    public ResponseEntity<Object> getAllUniversity(){
        return universityService.getAllUniversity();
    }
}
