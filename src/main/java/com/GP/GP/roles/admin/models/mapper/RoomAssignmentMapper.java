package com.GP.GP.roles.admin.models.mapper;


import com.GP.GP.entities.Room;
import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.request.RoomAssignmentRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.RoomAssignmentResponseDTO;
import lombok.Builder;

@Builder
public class RoomAssignmentMapper {
    public static RoomAssignmentResponseDTO mapToRoomAssignmentResponseDTO(User student, Room room) {
        return RoomAssignmentResponseDTO.builder()
                .roomId(room.getId())
                .buildingId(room.getBuilding().getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getType())
                .buildingName(room.getBuilding().getName())
                .studentName(student.getFirstName() + " " + student.getLastName())
                .studentEmail(student.getUsername())
                .studentPhoneNumber(student.getMobileNumber())
                .studentGender(student.getGender())
                .faculty(student.getFaculty())
                .level(student.getLevel())
                .build();
    }
    public static RoomAssignmentRequestDTO mapToRoomAssignmentRequestDTO(Room room, int userId) {
        return RoomAssignmentRequestDTO.builder()
                .userId(userId)
                .roomType(room.getType())
                .build();
    }

}
