package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.Building;
import com.GP.GP.entities.University;
import com.GP.GP.repository.BuildingRepository;
import com.GP.GP.roles.admin.models.dto.request.BuildingRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.BuildingResponseDTO;
import com.GP.GP.roles.admin.models.mapper.BuildingMapper;
import com.GP.GP.roles.admin.service.contracts.BuildingService;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private UniversityService universityService;

    @Override
    public ResponseEntity<Object> addBuilding(BuildingRequestDTO request) {
        University university = universityService.findUniversityById(request.getUniversityId());

        if (university == null) {
            return new ResponseEntity<>(new BaseResponse(false, "University not found"), HttpStatus.NOT_FOUND);
        }

        Building building = BuildingMapper.mapBuildingRequestToEntity(request , university);
        building = repository.save(building);

        BuildingResponseDTO responseDto = BuildingMapper.mapBuildingResponseToDto(building);
        return new ResponseEntity<>(new BaseResponse(true, "Building added successfully", responseDto), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<Object> getAllBuildings(int universityId) {
        University university = universityService.findUniversityById(universityId);

        if (university == null) {
            return new ResponseEntity<>(new BaseResponse(false, "No university found", null), HttpStatus.NOT_FOUND);
        }

        List<Building> buildings = repository.findByUniversity(university);
        if (buildings.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "No buildings found for this university", null), HttpStatus.NOT_FOUND);
        }

        List<BuildingResponseDTO> responseDTOs = buildings.stream()
                .map(BuildingMapper::mapBuildingResponseToDto)
                .toList();

        return new ResponseEntity<>(new BaseResponse(true, "Buildings retrieved successfully", responseDTOs), HttpStatus.OK);
    }
}
