package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.StudentSearchRequestDTO;
import org.springframework.http.ResponseEntity;

public interface StudentSearchService {
    ResponseEntity<Object> searchStudentByUsernameAndNationalId(StudentSearchRequestDTO request, int offset, int limit);
}
