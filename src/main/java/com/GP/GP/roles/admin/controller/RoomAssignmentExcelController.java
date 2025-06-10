package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.service.contracts.RoomAssignmentExcelService;
import com.GP.GP.roles.admin.service.contracts.RoomAssignmentService;
import com.GP.GP.roles.admin.service.contracts.RoomService;
import com.GP.GP.utill.Enums;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("")
public class RoomAssignmentExcelController {
    @Autowired
    private RoomAssignmentExcelService roomAssignmentExcelService;

    /*  @GetMapping("/admin/view/room-assignment/export-available-rooms")
      public ResponseEntity<byte[]> exportAvailableRooms(@RequestParam int buildingId,
                                                         @RequestParam Enums.RoomType roomType) throws IOException {
          ByteArrayInputStream excelFile = roomAssignmentExcelService.exportAvailableRoomsInBuildingToExcel(buildingId, roomType);

          HttpHeaders headers = new HttpHeaders();
          headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=available_rooms.xlsx");
          headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

          return ResponseEntity
                  .ok()
                  .headers(headers)
                  .body(excelFile.readAllBytes());
      }*/
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_UPLOAD_ROOM_ASSIGNMENT')")
    @PostMapping("/admin/edit/room-assignment/upload-student-housing-info")
    public ResponseEntity<Object> uploadStudentHousingInfo(@RequestParam("file") MultipartFile file) {
        return roomAssignmentExcelService.uploadStudentHousingInfo(file);
    }

    /*    @GetMapping("/admin/view/room-assignment/export-available-rooms-by-building-type")
        public ResponseEntity<byte[]> exportAvailableRoomsByBuildingType(@RequestParam Enums.BuildingType buildingType,
                                                                          @RequestParam Enums.RoomType roomType) throws IOException {
            ByteArrayInputStream excelFile = roomAssignmentExcelService.exportAvailableRoomsByBuildingTypeToExcel(buildingType, roomType);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=available_rooms_by_building_type.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(excelFile.readAllBytes());
        }*/
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_EXPORT_AVAILABLE_ROOM')")
    @GetMapping("/admin/view/room-assignment/export-available-rooms")
    public ResponseEntity<byte[]> exportAvailableRooms(
            @RequestParam(required = false) Integer buildingId,
            @RequestParam(required = false) Enums.BuildingType buildingType,
            @RequestParam Enums.RoomType roomType) throws IOException {

        ByteArrayInputStream excelFile =
                roomAssignmentExcelService.exportAvailableRoomsToExcel(buildingId, buildingType, roomType);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=available_rooms.xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(excelFile.readAllBytes());
    }

}
