package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.BuildingRequestDTO;
import com.GP.GP.roles.admin.service.contracts.BuildingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @PostMapping("/admin/add-buildings")
    public ResponseEntity<Object> addBuilding(@Valid @RequestBody BuildingRequestDTO request) {
        return buildingService.addBuilding(request);
    }
}
