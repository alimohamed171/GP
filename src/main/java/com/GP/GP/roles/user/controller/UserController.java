package com.GP.GP.roles.user.controller;

import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.request.AdmissionStatusNotesDTO;
import com.GP.GP.roles.user.model.dto.AdmissionRequestDTO;
import com.GP.GP.roles.user.model.mapper.AdmissionRequestMapper;
import com.GP.GP.roles.user.model.mapper.UserMapper;
import com.GP.GP.roles.user.model.request.UpdateUserRequestDTO;
import com.GP.GP.roles.user.model.request.UserFilterDTO;
import com.GP.GP.roles.user.model.response.UpdatedUserResponseDTO;
import com.GP.GP.roles.user.service.contracts.AdmissionRequestService;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private AdmissionRequestService admissionRequestService;

    @PostMapping("/user/admission-requests")
    public ResponseEntity<Object> applyForAdmission(@RequestBody AdmissionRequestDTO dto) {
        return admissionRequestService.createAdmissionRequest(dto);
    }

    @PutMapping("/user/admission-requests/{id}")
    public ResponseEntity<Object> updateRequest(@PathVariable int id, @RequestBody UpdateUserRequestDTO dto) {
        return admissionRequestService.updateUser(id, dto);
    }

    @GetMapping("/user/admission-requests/{id}/status")
    public ResponseEntity<Object> checkStatus(@PathVariable int id, @RequestParam int userId) {
        return admissionRequestService.checkApplicationStatus(id, userId);
    }

    // get all admission -> admin
    @GetMapping("/admin/view/admission-requests")
    public ResponseEntity<Object> getAllAdmissionRequests(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String securityCheck,
            @RequestParam(required = false) Boolean hasPenalty,
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {

        // Build filter DTO
        UserFilterDTO filterDTO = new UserFilterDTO();
        if (status != null) {
            filterDTO.setStatus(Arrays.stream(status.split(","))
                    .map(s -> Enums.AdmissionRequestStatues.valueOf(s.trim().toUpperCase()))
                    .collect(Collectors.toList()));
        }
        if (securityCheck != null) {
            filterDTO.setSecurityCheck(Arrays.stream(securityCheck.split(","))
                    .map(s -> Enums.SecurityCheckStatues.valueOf(s.trim().toUpperCase()))
                    .collect(Collectors.toList()));
        }
        filterDTO.setHasPenalty(hasPenalty);
        if (gender != null)
            filterDTO.setGender(Enums.Gender.valueOf(gender.trim().toUpperCase()));
        // Build pagination
        Pageable pageable = PageRequest.of(offset, limit);

        // Call the paginated service
        Page<User> pagedUsers = admissionRequestService.filterAdmissionRequests(filterDTO, pageable,true);

        if (pagedUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new BaseResponse(true, "No data found", HttpStatus.NO_CONTENT));
        }

        Page<UpdatedUserResponseDTO> pagedDTOs = pagedUsers.map(UserMapper::mapToUpdatedUserResponseDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("meta", createPageableResponse(pagedDTOs));
        response.put("data", pagedDTOs.getContent());

        return ResponseEntity.ok(response);

    }

    public static Map<String, Object> createPageableResponse(Page<?> page) {
        Map<String, Object> pageableResponse = new HashMap<>();
        pageableResponse.put("pageNumber", page.getNumber());
        pageableResponse.put("pageSize", page.getSize());
        pageableResponse.put("offset", page.getPageable().getOffset());
        pageableResponse.put("totalElements", page.getTotalElements());
        pageableResponse.put("totalPages", page.getTotalPages());
        pageableResponse.put("isLast", page.isLast());
        pageableResponse.put("isFirst", page.isFirst());
        return pageableResponse;
    }

    @GetMapping("/admin/all-admins")
    public ResponseEntity<Object> getAllAdmins(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String securityCheck,
            @RequestParam(required = false) Boolean hasPenalty,
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {

        // Build filter DTO
        UserFilterDTO filterDTO = new UserFilterDTO();
        if (status != null) {
            filterDTO.setStatus(Arrays.stream(status.split(","))
                    .map(s -> Enums.AdmissionRequestStatues.valueOf(s.trim().toUpperCase()))
                    .collect(Collectors.toList()));
        }
        if (securityCheck != null) {
            filterDTO.setSecurityCheck(Arrays.stream(securityCheck.split(","))
                    .map(s -> Enums.SecurityCheckStatues.valueOf(s.trim().toUpperCase()))
                    .collect(Collectors.toList()));
        }
        filterDTO.setHasPenalty(hasPenalty);
        if (gender != null)
            filterDTO.setGender(Enums.Gender.valueOf(gender.trim().toUpperCase()));
        // Build pagination
        Pageable pageable = PageRequest.of(offset, limit);

        // Call the paginated service
        Page<User> pagedUsers = admissionRequestService.filterAdmissionRequests(filterDTO, pageable,false);

        if (pagedUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new BaseResponse(true, "No data found", HttpStatus.NO_CONTENT));
        }

        Page<UpdatedUserResponseDTO> pagedDTOs = pagedUsers.map(UserMapper::mapToUpdatedUserResponseDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("meta", createPageableResponse(pagedDTOs));
        response.put("data", pagedDTOs.getContent());

        return ResponseEntity.ok(response);

    }
    //get admission by user Id -> for admin
    @GetMapping("/admin/view/admission-requests/{userId}")
    public ResponseEntity<Object> getAdmissionRequestByUserId(@PathVariable int userId) {
        return admissionRequestService.getAdmissionRequestByUserId(userId);
    }

    // need to add to collection for users
    @GetMapping("/user/admission-requests/{id}")
    public ResponseEntity<Object> getAdmissionRequestById(@PathVariable int id) {
        return admissionRequestService.getAdmissionRequestById(id);
    }

    // update statues ->admin (admissionId, enum.Admission status )
    @PutMapping("/admin/edit/admission-requests/{id}/status")
    public ResponseEntity<Object> updateAdmissionRequestStatus(@PathVariable int id, @RequestParam Enums.AdmissionRequestStatues status, @RequestBody AdmissionStatusNotesDTO statusNotes) {
        return admissionRequestService.updateAdmissionRequestStatues(id, status, statusNotes);
    }

    @GetMapping("/public/admission-requests/nid/{nationalId}/status")
    public ResponseEntity<Object> checkApplicationStatus(@PathVariable String nationalId) {
        return admissionRequestService.getApplicationStatusByNID(nationalId);
    }

    @GetMapping("/admin/view/sorted-applicants")
    public ResponseEntity<Object> getSortedApplicants() {
        return admissionRequestService.getSortedApplicants();
    }

}


