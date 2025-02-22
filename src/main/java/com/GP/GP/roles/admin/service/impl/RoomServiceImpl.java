package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.Building;
import com.GP.GP.entities.Room;
import com.GP.GP.repository.BuildingRepository;
import com.GP.GP.repository.RoomRepository;
import com.GP.GP.roles.admin.models.dto.request.RoomRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.RoomResponseDTO;
import com.GP.GP.roles.admin.models.mapper.RoomMapper;
import com.GP.GP.roles.admin.service.contracts.RoomService;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public ResponseEntity<Object> addRoom(RoomRequestDTO dto) {
        Optional<Building> building = buildingRepository.findById(dto.getBuildingId());

        if (building.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Building not found."), HttpStatus.NOT_FOUND);
        }

        Room room = RoomMapper.toRoomEntity(dto, building.get());
        room = roomRepository.save(room);

        RoomResponseDTO responseDTO = RoomMapper.toRoomResponseDTO(room);
        return new ResponseEntity<>(new BaseResponse(true, "Room added successfully.", responseDTO), HttpStatus.CREATED);
    }
}
