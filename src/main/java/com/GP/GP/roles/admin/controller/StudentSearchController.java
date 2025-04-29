package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.StudentSearchRequestDTO;
import com.GP.GP.roles.admin.service.contracts.StudentSearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class StudentSearchController {

    @Autowired
    private StudentSearchService studentSearchService;

    @PostMapping("/admin/view/search-student")
    public ResponseEntity<Object> searchStudent(@Valid @RequestBody StudentSearchRequestDTO request) {
        return studentSearchService.searchStudentByUsernameAndNationalId(request);
    }
}
