package com.GP.GP.roles.user.service.impl;

import com.GP.GP.entities.Appeal;
import com.GP.GP.entities.Complaint;
import com.GP.GP.entities.User;
import com.GP.GP.repository.AppealRepository;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.user.exception.ResourceNotFoundException;
import com.GP.GP.roles.user.model.mapper.AppealMapper;
import com.GP.GP.roles.user.model.mapper.ComplaintMapper;
import com.GP.GP.roles.user.model.request.AppealRequestDTO;
import com.GP.GP.roles.user.model.response.AppealResponseDTO;
import com.GP.GP.roles.user.model.response.ComplaintResponseDTO;
import com.GP.GP.roles.user.service.contracts.AppealService;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppealServiceImpl implements AppealService {
    @Autowired
    AppealRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> submitAppealRequest(Integer userId, AppealRequestDTO request) {

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();


        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
        if (loggedInUser.getId() != userId) {


            return new ResponseEntity<>(
                    new BaseResponse(false, "You are not authorized to create a complaint for this user.", null),
                    HttpStatus.FORBIDDEN);
        }

        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Target user not found"));
        Enums.AdmissionRequestStatues status = targetUser.getStatus();
        if (status != Enums.AdmissionRequestStatues.REJECTED &&
                status != Enums.AdmissionRequestStatues.UNDER_REVIEW) {
            return new ResponseEntity<>(
                    new BaseResponse(false, "You can only appeal if your status is REJECTED or UNDER_REVIEW.", null),
                    HttpStatus.FORBIDDEN);
        }

        if (repository.existsByUserAndStatus(targetUser, Enums.AdmissionRequestStatues.UNDER_REVIEW)) {
            return new ResponseEntity<>(
                    new BaseResponse(false, "You already submitted an appeal. Wait for a decision.", null),
                    HttpStatus.BAD_REQUEST);
        }

        Appeal appeal = new Appeal();
        appeal.setUser(targetUser);
        appeal.setReason(request.getReason());
        appeal.setCreatedAt(LocalDateTime.now());

        if (targetUser.getSecurityCheck() == Enums.SecurityCheckStatues.REJECTED) {
            appeal.setStatus(Enums.AdmissionRequestStatues.REJECTED);
        } else {
            appeal.setStatus(Enums.AdmissionRequestStatues.UNDER_REVIEW);
        }

        Appeal savedAppeal = repository.save(appeal);

        AppealResponseDTO appealResponseDTO = AppealMapper.entityToResponse(savedAppeal);

        BaseResponse response = new BaseResponse(true, "Your Appeal was submitted successfully!", appealResponseDTO);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> getAllAppeals() {
        List<Appeal> appeals = repository.findAll();
        List<AppealResponseDTO> dtos = appeals.stream()
                .map(AppealMapper::entityToResponse)
                .collect(Collectors.toList());
        BaseResponse response = new BaseResponse(true, "All appeals retrieved successfully", dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAppealById(Integer id) {
        Appeal appeal = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appeal not found with id: " + id));
        AppealResponseDTO responseDTO = AppealMapper.entityToResponse(appeal);
        BaseResponse response = new BaseResponse(true, "Appeal retrieved successfully", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateAppealStatus(int id, Enums.AdmissionRequestStatues status) {
        Appeal appeal = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appeal not found with id: " + id));

        if (appeal.getStatus() != Enums.AdmissionRequestStatues.UNDER_REVIEW) {
            return new ResponseEntity<>(
                    new BaseResponse(false, "Only appeals that are UNDER_REVIEW can be updated.", null),
                    HttpStatus.BAD_REQUEST);
        }


        User student = appeal.getUser();
        if (
                status == Enums.AdmissionRequestStatues.ACCEPTED && student.getSecurityCheck() == Enums.SecurityCheckStatues.ACCEPTED
        ) {
            student.setStatus(Enums.AdmissionRequestStatues.ACCEPTED);
            userRepository.save(student);
        } else if (status == Enums.AdmissionRequestStatues.REJECTED) {
            student.setStatus(Enums.AdmissionRequestStatues.REJECTED);
            userRepository.save(student);
        }else {
            return new ResponseEntity<>(
                    new BaseResponse(true, "request status is under review", null),
                    HttpStatus.BAD_REQUEST);
        }
        appeal.setStatus(status);
        repository.save(appeal);

        // Response
        AppealResponseDTO responseDTO = AppealMapper.entityToResponse(appeal);
        return new ResponseEntity<>(
                new BaseResponse(true, "Appeal status updated successfully.", responseDTO),
                HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> deleteAppeal(int id) {
        Appeal appeal = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appeal not found with id: " + id));

        if (appeal.getStatus() != Enums.AdmissionRequestStatues.UNDER_REVIEW) {
            return new ResponseEntity<>(
                    new BaseResponse(false, "Only appeals that are UNDER_REVIEW can be deleted.", null),
                    HttpStatus.BAD_REQUEST);
        }

        repository.delete(appeal);
        return new ResponseEntity<>(
                new BaseResponse(true, "Appeal deleted successfully.", null),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAppealsByUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Appeal> appeals = repository.findAll().stream()
                .filter(appeal -> appeal.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        if (appeals.isEmpty()) {
            return new ResponseEntity<>(
                    new BaseResponse(false, "No appeals found for this user.", null),
                    HttpStatus.NOT_FOUND);
        }

        List<AppealResponseDTO> responseDTOs = appeals.stream()
                .map(AppealMapper::entityToResponse)
                .collect(Collectors.toList());

        BaseResponse response = new BaseResponse(true, "Appeals retrieved successfully", responseDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAppealStatus(int userId) {
        User request = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission request not found"));
        String appealStatues = request.getStatus().name();
        BaseResponse response = new BaseResponse(true, "Application status retrieved successfully", appealStatues);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
