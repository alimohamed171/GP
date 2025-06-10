package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.Building;
import com.GP.GP.entities.Room;
import com.GP.GP.entities.User;
import com.GP.GP.repository.BuildingRepository;
import com.GP.GP.repository.RoomRepository;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.admin.service.contracts.RoomAssignmentExcelService;
import com.GP.GP.roles.admin.service.contracts.RoomAssignmentService;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomAssignmentExcelServiceImpl implements RoomAssignmentExcelService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomAssignmentService roomAssignmentService;
    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public ResponseEntity<Object> uploadStudentHousingInfo(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        List<String> errors = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    DataFormatter formatter = new DataFormatter();
                    Cell cell = row.getCell(9);
                    String nationalId = formatter.formatCellValue(cell).trim();
                    int roomId = (int) row.getCell(0).getNumericCellValue();

                    Optional<User> optionalUser = userRepository.findByNationalId(nationalId);
                    if (optionalUser.isEmpty()) {
                        errors.add("Student with national ID " + nationalId + " not found (row " + (i + 1) + ")");
                        continue;
                    }

                    int studentId = optionalUser.get().getId();

                    ResponseEntity<Object> response = roomAssignmentService.assignStudentSpecificRoom(studentId, roomId);

                    if (!((BaseResponse) response.getBody()).isSuccess()) {
                        errors.add("Row " + (i + 1) + ": " + ((BaseResponse) response.getBody()).getMessage());
                    }

                } catch (Exception e) {
                    errors.add("Error at row " + (i + 1) + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(errors);
        }

        return ResponseEntity.ok("All students assigned successfully");
    }

    @Override
    public ByteArrayInputStream exportAvailableRoomsInBuildingToExcel(int buildingId, Enums.RoomType roomType) throws IOException {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            throw new IllegalArgumentException("Building not found");
        }

        List<Room> availableRooms = roomRepository.findAvailableRoomsByBuildingAndRoomType(buildingId, roomType);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Available Rooms");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID ","رقم الغرقه","نوع السكن", "نوع المبنى", "اسم المبنى", "الاشغال الحالي", "السعه", "رقم الطابق", "الجناح", "الرقم القومي"};

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (Room room : availableRooms) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(room.getId());
                row.createCell(1).setCellValue(room.getRoomNumber());
                row.createCell(2).setCellValue(room.getType().toString());
                row.createCell(3).setCellValue(room.getBuilding().getType().toString());
                row.createCell(4).setCellValue(room.getBuilding().getName());
                row.createCell(5).setCellValue(room.getCurrentOccupancy());
                row.createCell(6).setCellValue(room.getCapacity());
                row.createCell(7).setCellValue(room.getFloorNumber());
                row.createCell(8).setCellValue(room.getWing());
                row.createCell(9).setCellValue("");
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
    @Override
    public ByteArrayInputStream exportAvailableRoomsByBuildingTypeToExcel(Enums.BuildingType buildingType, Enums.RoomType roomType) throws IOException {

        List<Room> availableRooms = roomRepository.findAvailableRoomsByBuildingTypeAndRoomType(buildingType, roomType);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Available Rooms");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID ","رقم الغرفه","نوع السكن", "نوع المبنى", "اسم المبنى", "الاشغال الحالي", "السعه", "رقم الطابق", "الجناح", "الرقم القومي"};

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (Room room : availableRooms) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(room.getId());
                row.createCell(1).setCellValue(room.getRoomNumber());
                row.createCell(2).setCellValue(room.getType().toString());
                row.createCell(3).setCellValue(room.getBuilding().getType().toString());
                row.createCell(4).setCellValue(room.getBuilding().getName());
                row.createCell(5).setCellValue(room.getCurrentOccupancy());
                row.createCell(6).setCellValue(room.getCapacity());
                row.createCell(7).setCellValue(room.getFloorNumber());
                row.createCell(8).setCellValue(room.getWing());
                row.createCell(9).setCellValue("");
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public ByteArrayInputStream exportAvailableRoomsToExcel(Integer buildingId, Enums.BuildingType buildingType, Enums.RoomType roomType) throws IOException {
            if (buildingId == null && buildingType == null) {
                throw new IllegalArgumentException("Either buildingId or buildingType must be provided.");
            }
            List<Room> availableRooms;

            if (buildingId != null) {
                if (!buildingRepository.existsById(buildingId)) {
                    throw new IllegalArgumentException("Building not found with ID: " + buildingId);
                }
                availableRooms = roomRepository.findAvailableRoomsByBuildingAndRoomType(buildingId, roomType);

            } else if (buildingType != null) {
                availableRooms = roomRepository.findAvailableRoomsByBuildingTypeAndRoomType(buildingType, roomType);

            } else {
                throw new IllegalArgumentException("Either buildingId or buildingType must be provided.");
            }

            try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                Sheet sheet = workbook.createSheet("Available Rooms");

                Row headerRow = sheet.createRow(0);
                String[] headers = {"ID", "رقم الغرفه", "نوع السكن", "نوع المبنى", "اسم المبنى", "الاشغال الحالي", "السعه", "رقم الطابق", "الجناح", "الرقم القومي"};

                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    sheet.autoSizeColumn(i);
                }

                int rowIdx = 1;
                for (Room room : availableRooms) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(room.getId());
                    row.createCell(1).setCellValue(room.getRoomNumber());
                    row.createCell(2).setCellValue(room.getType().toString());
                    row.createCell(3).setCellValue(room.getBuilding().getType().toString());
                    row.createCell(4).setCellValue(room.getBuilding().getName());
                    row.createCell(5).setCellValue(room.getCurrentOccupancy());
                    row.createCell(6).setCellValue(room.getCapacity());
                    row.createCell(7).setCellValue(room.getFloorNumber());
                    row.createCell(8).setCellValue(room.getWing());
                    row.createCell(9).setCellValue("");
                }

                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());
            }
    }
}

