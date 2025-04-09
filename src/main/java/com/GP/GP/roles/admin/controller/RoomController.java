package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.RoomRequestDTO;
import com.GP.GP.roles.admin.service.contracts.RoomService;
import com.GP.GP.utill.Enums;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class RoomController {

    @Autowired
    private RoomService roomService;


    @PostMapping("/admin/rooms/add")
    public ResponseEntity<Object> addRoom(@Valid @RequestBody RoomRequestDTO dto) {
        return roomService.addRoom(dto);
    }

    @GetMapping("/public/rooms/getAll/{buildingId}")
    public ResponseEntity<Object> getAllRooms(@PathVariable int buildingId) {
        return roomService.getAllRooms(buildingId);
    }

    @DeleteMapping("/admin/rooms/delete")
    public ResponseEntity<Object> deleteRoom(
            @RequestParam int buildingId,
            @RequestParam int roomId) {
        return roomService.deleteRoom(buildingId, roomId);
    }
    @GetMapping("/admin/rooms/get-room/{buildingId}/{roomId}")
    public ResponseEntity<Object> getRoomById(
            @PathVariable int buildingId,
            @PathVariable int roomId) {
        return roomService.getRoomById(buildingId, roomId);
    }
    @GetMapping("/admin/rooms/get-available/{buildingId}/{roomType}")
    public ResponseEntity<Object> getAvailableRooms(
            @PathVariable int buildingId,
            @PathVariable Enums.RoomType roomType) {
        return roomService.getAvailableRooms(buildingId, roomType);
    }
}
