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
    // Implement the methods from RoomAssignmentService interface here
    @Override
    public ResponseEntity<Object> assignStudentToRoom(RoomAssignmentRequestDTO dto) {
        // 1. Get student by ID
        Optional<User> optionalStudent = userRepository.findById(dto.getUserId());
        if (optionalStudent.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Student not found"), HttpStatus.NOT_FOUND);
        }
        User student = optionalStudent.get();
        // 2. Check if already assigned to a room
        if (student.getRoom() != null) {
            return new ResponseEntity<>(new BaseResponse(false, "Student already assigned to a room"), HttpStatus.BAD_REQUEST);
        }
        // 3. Check if student is accepted
        if (student.getStatus() != Enums.AdmissionRequestStatues.ACCEPTED) {
            return new ResponseEntity<>(new BaseResponse(false, "Student is not accepted and cannot be assigned to a room"), HttpStatus.FORBIDDEN);
        }
        // 3. Get building type from gender
        Enums.BuildingType buildingType = student.getGender() == Enums.Gender.FEMALE
                ? Enums.BuildingType.FEMALE
                : Enums.BuildingType.MALE;

        Optional<Building> building = buildingRepository.findByType(buildingType);

        if (building.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "No building found for the specified gender type: " + buildingType, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


        // 4. Find available room
        List<Room> rooms = roomRepository.findAvailableRoomsByGender(buildingType, dto.getRoomType());
        Optional<Room> optionalRoom = rooms.stream()
                .filter(room -> room.getCurrentOccupancy() < room.getCapacity()
                        && room.getStatus() == Enums.RoomStatus.AVAILABLE)
                .findFirst();
        // 5. Handle no room found
        if (optionalRoom.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No available room matching criteria"), HttpStatus.NOT_FOUND);
        }
        // 6. Assign room to student
        Room room = optionalRoom.get();
        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        student.setRoom(room);

        roomRepository.save(room);
        userRepository.save(student);
        // 7. Map response DTO
        RoomAssignmentResponseDTO responseDTO = RoomAssignmentMapper.mapToRoomAssignmentResponseDTO(student, room);
        return new ResponseEntity<>(new BaseResponse(true, "Room assigned successfully", responseDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeStudentFromRoom(int studentId, int roomId) {
        return null;
    }

}
