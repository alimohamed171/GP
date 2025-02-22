package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.RoomRequestDTO;
import com.GP.GP.roles.admin.service.contracts.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class RoomController {

    @Autowired
    private RoomService roomService;


    @PostMapping("/admin/rooms/add")
    public ResponseEntity<Object> addRoom(@Valid @RequestBody RoomRequestDTO dto) {
        return roomService.addRoom(dto);
    }
}
