package com.GP.GP.roles.Auth.service;


import com.GP.GP.entities.User;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.Auth.models.request.PrivilegesAssignmentRequestDTO;
import com.GP.GP.security.Privilege;
import com.GP.GP.repository.PrivilegeRepository;
import com.GP.GP.roles.Auth.models.mapper.PrivilegeMapper;
import com.GP.GP.roles.Auth.models.response.PrivilegeResponseDTO;
import com.GP.GP.utill.base.BaseResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PrivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<Object> getAllPrivileges() {
        List<Privilege> privileges = privilegeRepository.findAll();

        if (privileges.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "No privileges found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<PrivilegeResponseDTO> responseDTOs = privileges.stream()
                .map(PrivilegeMapper::mapToResponse)
                .toList();

        BaseResponse response = new BaseResponse(true, "Privileges retrieved successfully.", responseDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public ResponseEntity<Object> getPrivilegeById(Integer id) {
        Privilege privilege = privilegeRepository.findById(id).orElse(null);

        if (privilege == null) {
            BaseResponse response = new BaseResponse(false, "Privilege not found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        PrivilegeResponseDTO responseDTO = PrivilegeMapper.mapToResponse(privilege);
        BaseResponse response = new BaseResponse(true, "Privilege retrieved successfully.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<Object> getPrivilegesByUserId(Integer userId) {
        List<Privilege> privileges = privilegeRepository.findByUsersId(userId);

        if (privileges.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "No privileges found for this user.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<PrivilegeResponseDTO> responseDTOs = privileges.stream()
                .map(PrivilegeMapper::mapToResponse)
                .toList();

        BaseResponse response = new BaseResponse(true, "User privileges retrieved successfully.", responseDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Object> assignPrivilegeToUser (PrivilegesAssignmentRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new BaseResponse(false, "User not found.", null), HttpStatus.NOT_FOUND);
        }

        List<Privilege> privileges = privilegeRepository.findAllById(dto.getPrivilegeIds());
        if (privileges.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No privileges found for given IDs.", null), HttpStatus.BAD_REQUEST);
        }

        user.setPrivileges(privileges);
        userRepository.save(user);

        return new ResponseEntity<>(new BaseResponse(true, "Privileges assigned successfully.", null), HttpStatus.OK);

    }
    @Transactional
    public ResponseEntity<Object> revokePrivilegesFromUser(PrivilegesAssignmentRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new BaseResponse(false, "User not found.", null), HttpStatus.NOT_FOUND);
        }

        List<Privilege> toRemove = privilegeRepository.findAllById(dto.getPrivilegeIds());
        if (toRemove.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No privileges found for given IDs.", null), HttpStatus.BAD_REQUEST);
        }

        user.getPrivileges().removeAll(toRemove);
        userRepository.save(user);

        return new ResponseEntity<>(new BaseResponse(true, "Privileges removed successfully.", null), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Object> revokeAllPrivilegesFromUser(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new BaseResponse(false, "User not found.", null), HttpStatus.NOT_FOUND);
        }

        user.getPrivileges().clear();
        userRepository.save(user);

        return new ResponseEntity<>(new BaseResponse(true, "All privileges removed successfully.", null), HttpStatus.OK);
    }


}
