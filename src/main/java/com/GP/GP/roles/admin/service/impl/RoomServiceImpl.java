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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    @Override
    public ResponseEntity<Object> getAllRooms(int buildingId) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);

        if (optionalBuilding.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No building found"), HttpStatus.NOT_FOUND);
        }

        Building building = optionalBuilding.get();
        List<Room> rooms = roomRepository.findByBuildingId(buildingId);
        if (rooms.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No rooms found for this building"), HttpStatus.NOT_FOUND);
        }

        List<RoomResponseDTO> responseDTOs = rooms.stream()
                .map(RoomMapper::toRoomResponseDTO)
                .toList();

        return new ResponseEntity<>(new BaseResponse(true, "Rooms retrieved successfully", responseDTOs), HttpStatus.OK);
    }
}
