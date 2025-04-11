package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.RoomAssignmentRequestDTO;
import org.springframework.http.ResponseEntity;

public interface RoomAssignmentService {
    ResponseEntity<Object> assignStudentToRoom(RoomAssignmentRequestDTO dto);
    ResponseEntity<Object> removeStudentFromRoom(int studentId, int roomId);


}
