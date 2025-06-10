package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.BuildingRequestDTO;
import com.GP.GP.roles.admin.service.contracts.BuildingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_ADD_BUILDINGS')")
    @PostMapping("/admin/edit/add-buildings")
    public ResponseEntity<Object> addBuilding(@Valid @RequestBody BuildingRequestDTO request) {
        return buildingService.addBuilding(request);
    }
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_VIEW_BUILDINGS')")
    @GetMapping("/public/get-buildings/{universityId}")
    public ResponseEntity<Object> getAllBuildings(@PathVariable int universityId) {
        return buildingService.getAllBuildings(universityId);
    }
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_DELETE_BUILDINGS')")

    @DeleteMapping("/admin/delete-building")
    public ResponseEntity<Object> deleteBuilding(
            @RequestParam("universityId") int universityId,
            @RequestParam("buildingId") int buildingId) {
        return buildingService.deleteBuilding(universityId, buildingId);
    }
}
