package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.UniversityDTO;
import com.GP.GP.entities.University;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UniversityService {
    University findUniversityById(int id);

    ResponseEntity<Object> addUniversity(@Valid UniversityDTO request);


    ResponseEntity<Object> deleteUniversityById(int id);

    ResponseEntity<Object> updateUniversity(int id, @Valid UniversityDTO request);
}
