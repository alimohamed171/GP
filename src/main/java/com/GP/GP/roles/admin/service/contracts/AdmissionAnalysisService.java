package com.GP.GP.roles.admin.service.contracts;

import org.springframework.http.ResponseEntity;

public interface AdmissionAnalysisService {
    ResponseEntity<Object> analysisAdmissionRequest();
}
