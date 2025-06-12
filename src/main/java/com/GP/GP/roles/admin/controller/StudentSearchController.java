package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.StudentSearchRequestDTO;
import com.GP.GP.roles.admin.service.contracts.StudentSearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class StudentSearchController {

    @Autowired
    private StudentSearchService studentSearchService;

    @PostMapping("/admin/view/search-student")
    public ResponseEntity<Object> searchStudent(
            @RequestBody StudentSearchRequestDTO request,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {

        return studentSearchService.searchStudentByUsernameAndNationalId(request, offset, limit);
    }
}
