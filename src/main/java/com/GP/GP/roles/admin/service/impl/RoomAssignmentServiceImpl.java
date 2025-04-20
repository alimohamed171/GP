package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.Building;
import com.GP.GP.entities.Room;
import com.GP.GP.entities.User;
import com.GP.GP.repository.BuildingRepository;
import com.GP.GP.repository.RoomRepository;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.admin.models.dto.request.RoomAssignmentRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.RoomAssignmentResponseDTO;
import com.GP.GP.roles.admin.models.mapper.RoomAssignmentMapper;
import com.GP.GP.roles.admin.service.contracts.RoomAssignmentService;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomAssignmentServiceImpl implements RoomAssignmentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public ResponseEntity<Object> assignStudentToRoom(RoomAssignmentRequestDTO dto) {

        Optional<User> optionalStudent = userRepository.findById(dto.getUserId());
        if (optionalStudent.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Student not found"), HttpStatus.NOT_FOUND);
        }
        User student = optionalStudent.get();

        if (student.getRoom() != null) {
            return new ResponseEntity<>(new BaseResponse(false, "Student already assigned to a room"), HttpStatus.BAD_REQUEST);
        }

        if (student.getStatus() != Enums.AdmissionRequestStatues.ACCEPTED) {
            return new ResponseEntity<>(new BaseResponse(false, "Student is not accepted and cannot be assigned to a room"), HttpStatus.FORBIDDEN);
        }

        Enums.BuildingType buildingType = student.getGender() == Enums.Gender.FEMALE
                ? Enums.BuildingType.FEMALE
                : Enums.BuildingType.MALE;

        Optional<Building> building = buildingRepository.findByType(buildingType);

        if (building.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "No building found for the specified gender type: " + buildingType);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<Room> rooms = roomRepository.findAvailableRoomsByGender(buildingType, dto.getRoomType());
        Optional<Room> optionalRoom = rooms.stream()
                .filter(room -> room.getCurrentOccupancy() < room.getCapacity()
                        && room.getStatus() == Enums.RoomStatus.AVAILABLE)
                .findFirst();

        if (optionalRoom.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No available room matching criteria"), HttpStatus.NOT_FOUND);
        }
        Room room = optionalRoom.get();
        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        student.setRoom(room);

        roomRepository.save(room);
        userRepository.save(student);
        RoomAssignmentResponseDTO responseDTO = RoomAssignmentMapper.mapToRoomAssignmentResponseDTO(student, room);
        return new ResponseEntity<>(new BaseResponse(true, "Room assigned successfully", responseDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeStudentFromRoom(int studentId, int roomId) {
        Optional<User> optionalStudent = userRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Student not found"), HttpStatus.NOT_FOUND);
        }
        User student = optionalStudent.get();

        if (student.getRoom() == null) {
            return new ResponseEntity<>(new BaseResponse(false, "Student is not assigned to any room"), HttpStatus.BAD_REQUEST);
        }

        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Room not found"), HttpStatus.NOT_FOUND);
        }
        Room room = optionalRoom.get();

        if (!room.equals(student.getRoom())) {
            return new ResponseEntity<>(new BaseResponse(false, "Student is not assigned to this room"), HttpStatus.BAD_REQUEST);
        }

        room.setCurrentOccupancy(room.getCurrentOccupancy() - 1);
        student.setRoom(null);

        roomRepository.save(room);
        userRepository.save(student);

        return new ResponseEntity<>(new BaseResponse(true, "Student removed from room "+ room.getRoomNumber()+" successfully"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> assignStudentSpecificRoom(int studentId, int roomId) {
        Optional<User> optionalStudent = userRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Student not found"), HttpStatus.NOT_FOUND);
        }
        User student = optionalStudent.get();

        if (student.getRoom() != null) {
            return new ResponseEntity<>(new BaseResponse(false, "Student already assigned to a room"), HttpStatus.BAD_REQUEST);
        }

        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Room not found"), HttpStatus.NOT_FOUND);
        }
        Room room = optionalRoom.get();

        if (room.getCurrentOccupancy() >= room.getCapacity()) {
            return new ResponseEntity<>(new BaseResponse(false, "Room is full"), HttpStatus.BAD_REQUEST);
        }
        Enums.BuildingType buildingType = student.getGender() == Enums.Gender.FEMALE
                ? Enums.BuildingType.FEMALE
                : Enums.BuildingType.MALE;

        Optional<Building> building = buildingRepository.findByType(buildingType);

        if (building.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "No building found for the specified gender type: " + buildingType);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        if (!room.getBuilding().getType().equals(buildingType)) {
            return new ResponseEntity<>(new BaseResponse(false, "Room does not belong to the correct building type for the student"), HttpStatus.BAD_REQUEST);
        }

        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        student.setRoom(room);

        roomRepository.save(room);
        userRepository.save(student);

        RoomAssignmentResponseDTO responseDTO = RoomAssignmentMapper.mapToRoomAssignmentResponseDTO(student, room);
        return new ResponseEntity<>(new BaseResponse(true, "Room assigned successfully", responseDTO), HttpStatus.OK);
    }



}
