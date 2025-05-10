package com.GP.GP.roles.admin.service.contracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AdmissionRequestUploadService {
    ResponseEntity<Object> isValidExcelFile(MultipartFile file);
    ResponseEntity<Object> uploadAdmissionRequestSecurityCheck(MultipartFile file);
    ResponseEntity<Object> uploadAdmissionRequestStatusesFromExcel(MultipartFile file);

}
