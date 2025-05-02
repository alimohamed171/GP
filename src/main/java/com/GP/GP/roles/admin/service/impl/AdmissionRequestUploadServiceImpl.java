package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.User;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.admin.service.contracts.AdmissionRequestUploadService;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AdmissionRequestUploadServiceImpl implements AdmissionRequestUploadService {
    @Autowired
    UserRepository userRepository;
    @Override
    public ResponseEntity<Object> isValidExcelFile(MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(true, "file is empty!",null), HttpStatus.BAD_REQUEST);
        }
        String fileName = file.getOriginalFilename();
        if (fileName != null && (fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))) {
            return new ResponseEntity<>(new BaseResponse(true, "file is valid!",null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(true, "file is not valid!",null), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> uploadAdmissionRequestSecurityCheck(MultipartFile file) {
        List<String> errors = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            if (headerRow == null || headerRow.getPhysicalNumberOfCells() < 4) {
                errors.add("Invalid Excel format: Missing headers.");
                return ResponseEntity.badRequest().body(errors);
            }

            // Validate headers
            String nameHeader = headerRow.getCell(0).getStringCellValue().trim().toLowerCase();
            String nidHeader = headerRow.getCell(1).getStringCellValue().trim().toLowerCase();
            String statusHeader = headerRow.getCell(2).getStringCellValue().trim().toLowerCase();
            String notesHeader = headerRow.getCell(3).getStringCellValue().trim().toLowerCase();

            if (!nameHeader.contains("اسم الطالب") ||
                    !nidHeader.contains("الرقم القومي") ||
                    !statusHeader.contains("الفحص الأمني") ||
                    !notesHeader.contains("ملاحظات")) {
                errors.add("Invalid Excel format: Headers must be 'اسم الطالب', 'الرقم القومي للطالب', 'حاله الفحص الامني' and 'ملاحظات'.");
                return ResponseEntity.badRequest().body(errors);
            }

            int updatedCount = 0;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;


                try {
                    String nid = row.getCell(1).getStringCellValue().trim();
                    DataFormatter formatter = new DataFormatter();
                    String statusIndexStr = formatter.formatCellValue(row.getCell(2)).trim();
                    int statusIndex;
                    statusIndex = Integer.parseInt(statusIndexStr);
                    Enums.SecurityCheckStatues[] values = Enums.SecurityCheckStatues.values();

                    if (statusIndex < 0 || statusIndex >= values.length) {
                        errors.add("Invalid security check status index at row " + (i + 1) + ": " + statusIndexStr);
                        continue;
                    }
                    Enums.SecurityCheckStatues securityCheckStatus = values[statusIndex];
                    String notes = row.getCell(3) != null ? row.getCell(3).getStringCellValue().trim() : "";

                    // Lookup and update user by NID
                    Optional<User> optionalUser = userRepository.findByNationalId(nid);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        user.setSecurityCheck(securityCheckStatus); // Adjust based on your enum/type
                        user.setSecurityCheckNotes(notes);
                        userRepository.save(user);
                        updatedCount++;
                    } else {
                        errors.add("Student with national ID " + nid + " not found (row " + (i + 1) + ")");
                    }
                } catch (Exception e) {
                    errors.add("Error at row " + (i + 1) + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            errors.add("Error reading Excel file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            errors.add("Invalid Excel format: " + e.getMessage());
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(errors);
        }

        return ResponseEntity.ok("All students updated successfully");
    }



}
