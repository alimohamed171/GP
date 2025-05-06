package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.Building;
import com.GP.GP.entities.Room;
import com.GP.GP.repository.BuildingRepository;
import com.GP.GP.repository.RoomRepository;
import com.GP.GP.roles.admin.models.dto.request.RoomRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.RoomResponseDTO;
import com.GP.GP.roles.admin.models.mapper.RoomMapper;
import com.GP.GP.roles.admin.service.contracts.RoomService;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public ResponseEntity<Object> addRoom(RoomRequestDTO dto) {
        Optional<Building> buildingOpt = buildingRepository.findById(dto.getBuildingId());

        if (buildingOpt.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Building not found."), HttpStatus.NOT_FOUND);
        }
        Building building = buildingOpt.get();
        if (dto.getFloorNumber() > building.getFloorsCount() || dto.getFloorNumber()< 1) {
            return new ResponseEntity<>(
                    new BaseResponse(false, "Invalid floor number. Building has only " + building.getFloorsCount() + " floors."),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (roomRepository.existsByRoomNumberAndBuildingId(dto.getRoomNumber(), dto.getBuildingId())) {
            return new ResponseEntity<>(new BaseResponse(false, "Room number already exists in this building."), HttpStatus.BAD_REQUEST);
        }
        Room room = RoomMapper.toRoomEntity(dto, buildingOpt.get());
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

    @Override
    public ResponseEntity<Object> deleteRoom(int buildingId, int roomId) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No building found"), HttpStatus.NOT_FOUND);
        }

        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty() || optionalRoom.get().getBuilding().getId() != buildingId) {
            return new ResponseEntity<>(new BaseResponse(false, "No rooms found for this building"), HttpStatus.NOT_FOUND);
        }

        roomRepository.delete(optionalRoom.get());
        return new ResponseEntity<>(new BaseResponse(true, "Room deleted successfully"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getRoomById(int buildingId, int roomId) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No building found"), HttpStatus.NOT_FOUND);
        }

        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty() || optionalRoom.get().getBuilding().getId() != buildingId) {
            return new ResponseEntity<>(new BaseResponse(false, "No rooms found for this building"), HttpStatus.NOT_FOUND);
        }

        RoomResponseDTO responseDTO = RoomMapper.toRoomResponseDTO(optionalRoom.get());
        return new ResponseEntity<>(new BaseResponse(true, "Room retrieved successfully", responseDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAvailableRooms(int buildingId, Enums.RoomType roomType) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No building found"), HttpStatus.NOT_FOUND);
        }
        List<Room> availableRooms = roomRepository.findAvailableRoomsByBuildingAndRoomType(buildingId, roomType);

        if (availableRooms.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No available rooms found"), HttpStatus.NOT_FOUND);
        }
        List<RoomResponseDTO> responseDTOs = availableRooms.stream()
                .map(RoomMapper::toRoomResponseDTO)
                .toList();

        return new ResponseEntity<>(new BaseResponse(true, "Available rooms retrieved successfully", responseDTOs), HttpStatus.OK);

    }
}
