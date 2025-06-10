package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.service.contracts.AcceptedStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("")
public class AcceptedStudentController {

    @Autowired
    private AcceptedStudentService acceptedStudentService;
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_VIEW_ACCEPTED_STUDENTS')")
    @GetMapping("/admin/view/accepted-students")
    public ResponseEntity<Object> getAcceptedStudents() {
        return acceptedStudentService.getAcceptedStudents();
    }
}
