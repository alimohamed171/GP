package com.GP.GP.roles.user.service.contracts;

import com.GP.GP.roles.user.model.dto.AdmissionRequestDTO;
import com.GP.GP.roles.user.model.request.UpdateUserRequestDTO;
import com.GP.GP.utill.Enums;
import org.springframework.http.ResponseEntity;

public interface AdmissionRequestService {
    ResponseEntity<Object> createAdmissionRequest(AdmissionRequestDTO admissionRequestDTO);
    ResponseEntity<Object> updateUser(int id, UpdateUserRequestDTO updateUserRequestDTO);
    ResponseEntity<Object> checkApplicationStatus(int id, int userId);
    ResponseEntity<Object> getAllAdmissionRequests();
    ResponseEntity<Object> getAdmissionRequestByUserId(int uerId);
    ResponseEntity<Object> updateAdmissionRequestStatues(int id, Enums.AdmissionRequestStatues status);
    ResponseEntity<Object> getApplicationStatusByNID(String nationalId);


}
