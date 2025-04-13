package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.service.contracts.AcceptedStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AcceptedStudentController {

    @Autowired
    private AcceptedStudentService acceptedStudentService;

    @GetMapping("/public/accepted-students")
    public ResponseEntity<Object> getAcceptedStudents() {
        return acceptedStudentService.getAcceptedStudents();
    }
}
