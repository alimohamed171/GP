package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.service.impl.AdminActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AdminActionLogController {
    @Autowired
    private AdminActionLogService adminActionLogService;


    @GetMapping("/admin/get-logs")
    public ResponseEntity<Object> getAllLogs() {
        return adminActionLogService.getAllLogs();
    }
}
