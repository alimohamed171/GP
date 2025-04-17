package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.PenaltyDTO;
import com.GP.GP.roles.admin.service.contracts.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PenaltyController {
    @Autowired
    private PenaltyService penaltyService;
    @PostMapping("/admin/edit/add-penalty")
    public ResponseEntity<Object> createPenalty(@RequestBody PenaltyDTO penaltyDTO) {
        return penaltyService.createPenalty(penaltyDTO);
    }
    //admin and user
    @GetMapping("/admin/view/get-penalty/{id}")
    public ResponseEntity<Object> getPenaltyById(@PathVariable int id) {
        return penaltyService.getPenaltyById(id);
    }
    @GetMapping("/admin/view/get-all-penalties")
    public ResponseEntity<Object> getAllPenalties() {
        return penaltyService.getAllPenalties();
    }
    @GetMapping("/admin/view/get-all-user-penalties/{userId}")
    public ResponseEntity<Object> getPenaltiesByUserId(@PathVariable int userId) {
        return penaltyService.getPenaltiesByUserId(userId);
    }
    @DeleteMapping("/admin/delete-penalty/{id}")
    public ResponseEntity<Object> deletePenalty(@PathVariable int id) {
        return penaltyService.deletePenalty(id);
    }

}
