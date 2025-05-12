package com.GP.GP.roles.user.service.contracts;

import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.request.AdmissionStatusNotesDTO;
import com.GP.GP.roles.user.model.dto.AdmissionRequestDTO;
import com.GP.GP.roles.user.model.request.UpdateUserRequestDTO;
import com.GP.GP.roles.user.model.request.UserFilterDTO;
import com.GP.GP.roles.user.model.response.StudentsGroupedResponseDTO;
import com.GP.GP.utill.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdmissionRequestService {
    ResponseEntity<Object> createAdmissionRequest(AdmissionRequestDTO admissionRequestDTO);
    ResponseEntity<Object> updateUser(int id, UpdateUserRequestDTO updateUserRequestDTO);
    ResponseEntity<Object> checkApplicationStatus(int id, int userId);
    ResponseEntity<Object> getAllAdmissionRequests(List<User> filteredRequests);
    ResponseEntity<Object> getAdmissionRequestByUserId(int uerId);
    ResponseEntity<Object> getAdmissionRequestById(int id);
    ResponseEntity<Object> updateAdmissionRequestStatues(int id, Enums.AdmissionRequestStatues status, AdmissionStatusNotesDTO statusNotes);
    ResponseEntity<Object> getApplicationStatusByNID(String nationalId);
    Page<User> filterAdmissionRequests(UserFilterDTO filteredRequests, Pageable pageable);

    ResponseEntity<Object> getSortedApplicants();
    StudentsGroupedResponseDTO getSortedApplicantsData();

}
