package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.ApplicationDeadlineDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface ApplicationDeadlineService {
    ResponseEntity<Object> addApplicationDeadline(int universityId, @Valid ApplicationDeadlineDTO dto);
}
