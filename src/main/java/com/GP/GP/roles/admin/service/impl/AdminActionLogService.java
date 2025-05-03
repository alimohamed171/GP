package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.AdminActionLog;
import com.GP.GP.repository.AdminActionLogRepository;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminActionLogService {

    @Autowired
    private AdminActionLogRepository logRepository;

    public ResponseEntity<Object> getAllLogs() {


        List<AdminActionLog> Logs = logRepository.findAll();
        if (Logs.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No Logs found ", null), HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(new BaseResponse(true, "Logs retrieved successfully", Logs), HttpStatus.OK);
    }

}
