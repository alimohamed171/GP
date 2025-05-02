package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.utill.Enums;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface RoomAssignmentExcelService {
    ResponseEntity<Object> uploadStudentHousingInfo(MultipartFile file);
    ByteArrayInputStream exportAvailableRoomsInBuildingToExcel(int buildingId, Enums.RoomType roomType) throws IOException;

}
