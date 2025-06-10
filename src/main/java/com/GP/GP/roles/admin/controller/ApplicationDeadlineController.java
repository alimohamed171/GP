package com.GP.GP.roles.admin.controller;
import com.GP.GP.roles.admin.models.dto.request.ApplicationDeadlineDTO;
import com.GP.GP.roles.admin.service.contracts.ApplicationDeadlineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ApplicationDeadlineController {

    @Autowired
    private ApplicationDeadlineService applicationDeadlineService;
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_ADD_APPLICATION_DEADLINES')")
    @PostMapping("/admin/edit/application-deadline/{universityId}")
    ResponseEntity<Object> addApplicationDeadline(@PathVariable int universityId, @Valid @RequestBody ApplicationDeadlineDTO dto){
        return applicationDeadlineService.addApplicationDeadline(universityId, dto);
    }
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_DELETE_APPLICATION_DEADLINES')")
    @DeleteMapping("/admin/delete-application-deadline")
    public ResponseEntity<Object> deleteApplicationDeadline(@RequestParam int deadlineId,@RequestParam int universityId) {
        return applicationDeadlineService.deleteApplicationDeadlineById(deadlineId, universityId);
    }
    @GetMapping("/public/all-deadlines/university/{universityId}")
    public ResponseEntity<Object> getAllDeadlinesByUniversity(@PathVariable int universityId) {
        return applicationDeadlineService.getAllAppDeadlinesByUniversityId(universityId);
    }
    @PreAuthorize("@accessChecker.hasPrivilegeOrIsAdmin(authentication, 'ACCESS_UPDATE_APPLICATION_DEADLINES')")
    @PutMapping("/admin/edit/update-application-deadline")
    public ResponseEntity<Object> updateApplicationDeadline(
            @RequestParam int deadlineId,
            @RequestParam int universityId,
            @Valid @RequestBody ApplicationDeadlineDTO dto) {
        return applicationDeadlineService.updateApplicationDeadline(deadlineId, universityId, dto);
    }

}
