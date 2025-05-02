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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
            @RequestParam(required = false) Boolean specialNeeds,
            @RequestParam(required = false) String studentType,
            @RequestParam(required = false) String securityCheck,
            @RequestParam(required = false) Boolean hasPenalty,
            @RequestParam(required = false) String columns
    ) throws IOException {

        // Create a DTO with the filter parameters
        AdmissionRequestFilterDTO filterDTO = new AdmissionRequestFilterDTO();
        if (status != null) {
            filterDTO.setStatus(Arrays.stream(status.split(","))
                    .map(s -> Enums.AdmissionRequestStatues.valueOf(s.trim().toUpperCase()))
                    .collect(Collectors.toList()));
        }
        if (gender != null) {
            filterDTO.setGender(Enums.Gender.valueOf(gender.toUpperCase()));
        }
        if (universityName != null) {
            filterDTO.setUniversityName(Arrays.asList(universityName.split(",")));
        }
        if (faculty != null) {
            filterDTO.setFaculty(Arrays.asList(faculty.split(",")));
        }
        if (level != null) {
            filterDTO.setLevel(Arrays.asList(level.split(",")));
        }
        filterDTO.setSpecialNeeds(specialNeeds);
        filterDTO.setStartDate(from);
        filterDTO.setEndDate(to);
        if (studentType != null) {
            filterDTO.setStudentType(Enums.StudentType.valueOf(studentType.toUpperCase()));
        }
        if (securityCheck != null) {
            filterDTO.setSecurityCheck(Arrays.stream(securityCheck.split(","))
                    .map(s -> Enums.SecurityCheckStatues.valueOf(s.trim().toUpperCase()))
                    .collect(Collectors.toList()));
        }
        filterDTO.setHasPenalty(hasPenalty);

        // Fetch filtered data from the service
        List<User> filteredRequests = exportService.filterAdmissionRequests(filterDTO);

        if (filteredRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse(false, "No data found", HttpStatus.NO_CONTENT));
        }
        List<String> selectedColumns = columns != null && !columns.isBlank()
                ? Arrays.asList(columns.split(","))
                : null;
        // Generate Excel file
        ByteArrayInputStream excelFile = exportService.exportFilteredAdmissionRequestsToExcel(filteredRequests, selectedColumns);

        // Set response headers for file download
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=admission_requests_filtered_" + ".xlsx");

        // Return the file as the response
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(excelFile.readAllBytes());
    }

    @GetMapping("/admin/view/security-check/template")
    public void downloadSecurityCheckTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=security_check_template.xlsx");

        ByteArrayInputStream template = exportService.generateSecurityCheckTemplate();
        IOUtils.copy(template, response.getOutputStream());
        response.flushBuffer();
    }

}
