package com.GP.GP.roles.user.service.impl;

import com.GP.GP.entities.Complaint;
import com.GP.GP.entities.User;
import com.GP.GP.repository.ComplaintRepository;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.user.exception.ResourceNotFoundException;
import com.GP.GP.roles.user.model.mapper.ComplaintMapper;
import com.GP.GP.roles.user.model.request.ComplaintRequestDTO;
import com.GP.GP.roles.user.model.response.ComplaintResponseDTO;
import com.GP.GP.roles.user.service.contracts.ComplaintService;
import com.GP.GP.security.Role;
import com.GP.GP.utill.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComplaintRepository complaintRepository;


    @Override
    public ResponseEntity<Object> createComplaint(ComplaintRequestDTO request, int userId) {


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

        Complaint complaint = ComplaintMapper.requestToEntity(request);
        complaint.setUser(targetUser);
        complaint.setCreatedAt(LocalDateTime.now());

        Complaint savedComplaint = complaintRepository.save(complaint);

        ComplaintResponseDTO complaintResponseDTO = ComplaintMapper.entityToResponse(savedComplaint);

        BaseResponse response = new BaseResponse(true, "Your complaint was submitted successfully!", complaintResponseDTO);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        List<ComplaintResponseDTO> dtos = complaints.stream()
                .map(ComplaintMapper::entityToResponse)
                .collect(Collectors.toList());
        BaseResponse response = new BaseResponse(true, "All complaints retrieved successfully", dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getComplaintById(int id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElse(null);

        if (complaint == null) {
            BaseResponse response = new BaseResponse(false, "Complaint not found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        ComplaintResponseDTO complaintResponseDTO = ComplaintMapper.entityToResponse(complaint);
        BaseResponse response = new BaseResponse(true, "Complaint with ID:"+id+" retrieved successfully!", complaintResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateComplaint(int id, ComplaintRequestDTO request) {
        Complaint existingComplaint = complaintRepository.findById(id)
                .orElseThrow(null);
        if (existingComplaint == null) {
            BaseResponse response = new BaseResponse(false, "Complaint not found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        existingComplaint.setComplaintText(request.getComplaintText());
        Complaint updatedComplaint = complaintRepository.save(existingComplaint);
        ComplaintResponseDTO complaintResponseDTO = ComplaintMapper.entityToResponse(updatedComplaint);
        BaseResponse response = new BaseResponse(true, "Complaint updated successfully!", complaintResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteComplaint(int id) {
        if (complaintRepository.existsById(id)) {
            complaintRepository.deleteById(id);
            BaseResponse response = new BaseResponse(true, "Complaint deleted successfully!.", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        BaseResponse response = new BaseResponse(false, "Complaint not found.", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getComplaintsByUser(int userId) {
        if (!userRepository.existsById(userId)) {
            BaseResponse response = new BaseResponse(false, "Complaint not found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        List<Complaint> complaints = complaintRepository.findByUserId(userId);
        List<ComplaintResponseDTO> dtos = complaints.stream()
                .map(ComplaintMapper::entityToResponse)
                .toList();
        BaseResponse response = new BaseResponse(true, "Complaints of user ID:"+userId+" retrieved successfully", dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
