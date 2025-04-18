package com.GP.GP.roles.admin.controller;

import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.request.AdmissionRequestFilterDTO;
import com.GP.GP.roles.admin.service.contracts.AdmissionRequestExportService;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("")
public class AdmissionRequestExportController {
    @Autowired
    private AdmissionRequestExportService exportService;

    @GetMapping("/admin/view/export-admission-requests")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=admission_requests.xlsx");

        ByteArrayInputStream excelFile = exportService.exportAllAdmissionRequestsToExcel();
        IOUtils.copy(excelFile, response.getOutputStream());
        response.flushBuffer();
    }
    @GetMapping("/admin/view/admission-requests/export")
    public ResponseEntity<Object> exportAdmissionRequestsToExcel(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String universityName,
            @RequestParam(required = false) String faculty,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) Boolean specialNeeds) throws IOException {

        LocalDateTime startDate = from;
        LocalDateTime endDate = to;

        // Create a DTO with the filter parameters
        AdmissionRequestFilterDTO filterDTO = new AdmissionRequestFilterDTO();
        if (status != null) {
            filterDTO.setStatus(Enums.AdmissionRequestStatues.valueOf(status.toUpperCase()));
        }
        if (gender != null) {
            filterDTO.setGender(Enums.Gender.valueOf(gender.toUpperCase()));
        }
        filterDTO.setUniversityName(universityName);
        filterDTO.setFaculty(faculty);
        filterDTO.setLevel(level);
        filterDTO.setSpecialNeeds(specialNeeds);
        filterDTO.setStartDate(startDate);
        filterDTO.setEndDate(endDate);

        // Fetch filtered data from the service
        List<User> filteredRequests = exportService.filterAdmissionRequests(filterDTO);

        if (filteredRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse(false,"No data found", HttpStatus.NO_CONTENT));
        }

        // Generate Excel file
        ByteArrayInputStream excelFile = exportService.exportFilteredAdmissionRequestsToExcel(filteredRequests);

        // Set response headers for file download
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=admission_requests_filtered.xlsx");

        // Return the file as the response
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(excelFile.readAllBytes());
    }
}
