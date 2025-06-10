package com.GP.GP.roles.user.controller;

import com.GP.GP.roles.user.model.request.AppealRequestDTO;
import com.GP.GP.roles.user.service.contracts.AppealService;
import com.GP.GP.utill.Enums;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppealController {
    @Autowired
    AppealService appealService;
    @PostMapping("/user/submit-appeal/{userId}")
    public ResponseEntity<Object> submitAppeal( @PathVariable int userId, @Valid @RequestBody AppealRequestDTO dto) {
        return appealService.submitAppealRequest(userId, dto);
    }
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_VIEW_APPEALS')")
    @GetMapping("/admin/view/appeals")
    public ResponseEntity<Object> getAllAppeals() {
        return appealService.getAllAppeals();
    }

    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_VIEW_APPEALS')")
    @GetMapping("/admin/view/appeal/{id}")
    public ResponseEntity<Object> getAppealById(@PathVariable Integer id) {
        return appealService.getAppealById(id);
    }

    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_UPDATE_APPEAL_STATUS')")
    @PutMapping("/admin/edit/update-appeal-status/{id}")
    public ResponseEntity<Object> updateAppealStatus(@PathVariable int id, @RequestParam("status") Enums.AdmissionRequestStatues status) {
        return appealService.updateAppealStatus(id, status);
    }

    @DeleteMapping("/user/delete-appeal/{id}")
    public ResponseEntity<Object> deleteAppeal(@PathVariable int id) {
        return appealService.deleteAppeal(id);
    }

    @GetMapping("/user/my-appeals/{userId}")
    public ResponseEntity<Object> getAppealsByUser(@PathVariable int userId) {
        return appealService.getAppealsByUser(userId);
    }
    @GetMapping("/user/appeal-status/{userId}")
    public ResponseEntity<Object> getAppealStatus(@PathVariable int userId) {
        return appealService.getAppealStatus(userId);
    }


}
