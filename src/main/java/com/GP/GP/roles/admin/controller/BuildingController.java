package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.BuildingRequestDTO;
import com.GP.GP.roles.admin.service.contracts.BuildingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @PostMapping("/admin/edit/add-buildings")
    public ResponseEntity<Object> addBuilding(@Valid @RequestBody BuildingRequestDTO request) {
        return buildingService.addBuilding(request);
    }

    @GetMapping("/public/get-buildings/{universityId}")
    public ResponseEntity<Object> getAllBuildings(@PathVariable int universityId) {
        return buildingService.getAllBuildings(universityId);
    }

    @DeleteMapping("/admin/delete-building")
    public ResponseEntity<Object> deleteBuilding(
            @RequestParam("universityId") int universityId,
            @RequestParam("buildingId") int buildingId) {
        return buildingService.deleteBuilding(universityId, buildingId);
    }
}
