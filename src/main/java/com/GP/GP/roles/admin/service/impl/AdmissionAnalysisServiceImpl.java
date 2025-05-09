package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.entities.User;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.admin.models.dto.request.GenderAnalysisDTO;
import com.GP.GP.roles.admin.models.dto.response.GenderAnalysisResponseDTO;
import com.GP.GP.roles.admin.service.contracts.AdmissionAnalysisService;
import com.GP.GP.roles.user.service.contracts.AdmissionRequestService;
import com.GP.GP.security.Role;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmissionAnalysisServiceImpl implements AdmissionAnalysisService {
    @Autowired
    UserRepository userRepository;



    @Override
    public ResponseEntity<Object> analysisAdmissionRequest() {
        List<Role> userRoles = List.of(Role.EDIT_ADMIN, Role.ADMIN, Role.ViEW_ADMIN);
        List<User> requests = userRepository.findByRoleNotIn(userRoles);
        GenderAnalysisResponseDTO response = new GenderAnalysisResponseDTO();
        for (User request : requests) {
            Enums.Gender gender = request.getGender();
            Enums.AdmissionRequestStatues status = request.getStatus();
            GenderAnalysisDTO genderData = (gender == Enums.Gender.FEMALE)
                    ? response.getFemale()
                    : response.getMale();
            genderData.setTotal(genderData.getTotal() + 1);
            switch (status) {
                case UNDER_REVIEW -> genderData.setUnderReview(genderData.getUnderReview() + 1);
                case ACCEPTED -> genderData.setAccepted(genderData.getAccepted() + 1);
                case REJECTED -> genderData.setRejected(genderData.getRejected() + 1);

            }
        }
        return new ResponseEntity<>(new BaseResponse(true, "analysis done!", response), HttpStatus.OK);
    }
}
