package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.Penalty;
import com.GP.GP.entities.User;
import com.GP.GP.repository.PenaltyRepository;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.admin.models.dto.request.PenaltyDTO;
import com.GP.GP.roles.admin.models.dto.response.PenaltyResponseDTO;
import com.GP.GP.roles.admin.models.mapper.PenaltyMapper;
import com.GP.GP.roles.admin.service.contracts.PenaltyService;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PenaltyServiceImpl implements PenaltyService {
    @Autowired
    private PenaltyRepository penaltyRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> createPenalty(PenaltyDTO penaltyDTO) {
        if (! userRepository.existsById(penaltyDTO.getUserId())){
            BaseResponse response= new BaseResponse(false, "User not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findById(penaltyDTO.getUserId()).get();
        Penalty penalty = PenaltyMapper.toEntity(penaltyDTO, user);
        penalty = penaltyRepository.save(penalty);
        PenaltyResponseDTO penaltyResponseDTO = PenaltyMapper.toDTO(penalty);
        BaseResponse response = new BaseResponse(true, "penalty submitted successfully!", penaltyResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getPenaltyById(int id) {
        if (!penaltyRepository.existsById(id)){
            BaseResponse response= new BaseResponse(false, "Penalty not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Penalty penalty = penaltyRepository.findById(id).get();
        PenaltyResponseDTO penaltyResponseDTO = PenaltyMapper.toDTO(penalty);
        BaseResponse response = new BaseResponse(true, "Penalty retrieved successfully!", penaltyResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllPenalties() {
        List<PenaltyResponseDTO> penalties = penaltyRepository.findAll().stream()
                .map(PenaltyMapper::toDTO)
                .collect(Collectors.toList());
        BaseResponse response = new BaseResponse(true, "Penalties retrieved successfully!", penalties);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deletePenalty(int id) {
        if (!penaltyRepository.existsById(id)) {
            BaseResponse response= new BaseResponse(false, "Penalty not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        penaltyRepository.deleteById(id);
        BaseResponse response = new BaseResponse(true, "Penalty deleted successfully!", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getPenaltiesByUserId(int userId) {
        if (!userRepository.existsById(userId)){
            BaseResponse response= new BaseResponse(false, "user not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findById(userId).get();

        List<PenaltyResponseDTO> penalties = penaltyRepository.findByUser(user).stream()
                .map(PenaltyMapper::toDTO)
                .collect(Collectors.toList());
        BaseResponse response = new BaseResponse(true, "User's penalties retrieved successfully!", penalties);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
