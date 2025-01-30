package com.GP.GP.roles.user.service.contracts;

import com.GP.GP.roles.user.dto.AdmissionRequestDTO;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface AdmissionRequestService {
    ResponseEntity<Object> createAdmissionRequest(AdmissionRequestDTO admissionRequestDTO);
    ResponseEntity<Object> updateAdmissionRequest(int id, AdmissionRequestDTO admissionRequestDTO);
    ResponseEntity<Object> checkApplicationStatus(int id, int userId);

    

}
