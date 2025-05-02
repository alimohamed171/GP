package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.request.AdmissionRequestFilterDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface AdmissionRequestExportService {
    ByteArrayInputStream exportAllAdmissionRequestsToExcel();

    List<User> filterAdmissionRequests(AdmissionRequestFilterDTO filterDTO);
    ByteArrayInputStream exportFilteredAdmissionRequestsToExcel(List<User> filteredRequests, List<String> selectedColumns);
    ByteArrayInputStream generateSecurityCheckTemplate();

}