package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.BuildingRequestDTO;
import org.springframework.http.ResponseEntity;

public interface BuildingService {
    ResponseEntity<Object> addBuilding(BuildingRequestDTO request);
    ResponseEntity<Object> getAllBuildings(int universityId);
}
