package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.RoomAssignmentRequestDTO;
import com.GP.GP.roles.admin.models.dto.request.RoomRequestDTO;
import com.GP.GP.roles.admin.service.contracts.RoomAssignmentService;
import com.GP.GP.roles.admin.service.contracts.RoomService;
import com.GP.GP.utill.Enums;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomAssignmentService roomAssignmentService;


    @PostMapping("/admin/edit/rooms/add")
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
    @GetMapping("/admin/view/rooms/get-room/{buildingId}/{roomId}")
    public ResponseEntity<Object> getRoomById(
            @PathVariable int buildingId,
            @PathVariable int roomId) {
        return roomService.getRoomById(buildingId, roomId);
    }
    @GetMapping("/admin/view/rooms/get-available/{buildingId}/{roomType}")
    public ResponseEntity<Object> getAvailableRooms(
            @PathVariable int buildingId,
            @PathVariable Enums.RoomType roomType) {
        return roomService.getAvailableRooms(buildingId, roomType);
    }
    @GetMapping("/admin/view/rooms/get-available-by-building-type/{buildingType}/{roomType}")
    public ResponseEntity<Object> getAvailableRoomsByBuildingTypeAndRoomType(
            @PathVariable Enums.BuildingType buildingType,
            @PathVariable Enums.RoomType roomType) {
        return roomService.getAvailableRoomsByBuildingTypeAndRoomType(buildingType, roomType);
    }

    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_ASSIGNMENT')")
    @PostMapping("/admin/edit/rooms/assign-room")
    public ResponseEntity<Object> assignRoomToStudent(@RequestBody RoomAssignmentRequestDTO roomAssignmentRequestDTO) {
        return roomAssignmentService.assignStudentToRoom(roomAssignmentRequestDTO);
    }
    @DeleteMapping("/admin/rooms/remove-student")
    public ResponseEntity<Object> removeStudentFromRoom(
            @RequestParam int studentId,
            @RequestParam int roomId) {
        return roomAssignmentService.removeStudentFromRoom(studentId, roomId);
    }

    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_ACCOMMODATION')")
    @PostMapping("/admin/edit/rooms/assign-student-specific-room")
    public ResponseEntity<Object> assignStudentToSpecificRoom(
            @RequestParam int studentId,
            @RequestParam int roomId) {
        return roomAssignmentService.assignStudentSpecificRoom(studentId, roomId);
    }

}
