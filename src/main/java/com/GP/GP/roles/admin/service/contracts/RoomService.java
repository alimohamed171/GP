package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.RoomRequestDTO;
import org.springframework.http.ResponseEntity;

public interface RoomService {
    ResponseEntity<Object> addRoom(RoomRequestDTO dto);
    ResponseEntity<Object> getAllRooms(int buildingId);
}
