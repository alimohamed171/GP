package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.PenaltyDTO;
import org.springframework.http.ResponseEntity;

public interface PenaltyService {
    ResponseEntity<Object> createPenalty(PenaltyDTO penaltyDTO);
    ResponseEntity<Object>getPenaltyById(int id) ;
    ResponseEntity<Object>  getAllPenalties();
    ResponseEntity<Object>deletePenalty(int id) ;
    ResponseEntity<Object>getPenaltiesByUserId(int userId);


}
