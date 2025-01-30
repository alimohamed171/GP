package com.GP.GP.roles.user.service.contracts;

import com.GP.GP.roles.user.dto.AdmissionRequestDTO;

public interface AdmissionRequestService {
    AdmissionRequestDTO createAdmissionRequest(AdmissionRequestDTO admissionRequestDTO);
    AdmissionRequestDTO updateAdmissionRequest(int id, AdmissionRequestDTO admissionRequestDTO);
    String checkApplicationStatus(int id, int userId);

}
