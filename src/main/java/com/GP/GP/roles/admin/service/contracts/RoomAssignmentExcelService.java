package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.utill.Enums;
import io.micrometer.common.lang.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface RoomAssignmentExcelService {
    ResponseEntity<Object> uploadStudentHousingInfo(MultipartFile file);
    ByteArrayInputStream exportAvailableRoomsInBuildingToExcel(int buildingId, Enums.RoomType roomType) throws IOException;
    ByteArrayInputStream exportAvailableRoomsByBuildingTypeToExcel( Enums.BuildingType buildingType, Enums.RoomType roomType) throws IOException;
    ByteArrayInputStream exportAvailableRoomsToExcel(
            @Nullable Integer buildingId,
            @Nullable Enums.BuildingType buildingType,
            Enums.RoomType roomType
    ) throws IOException;

}
