package com.GP.GP.roles.admin.controller;
import com.GP.GP.roles.admin.models.dto.request.ApplicationDeadlineDTO;
import com.GP.GP.roles.admin.service.contracts.ApplicationDeadlineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ApplicationDeadlineController {

    @Autowired
    private ApplicationDeadlineService applicationDeadlineService;

    @PostMapping("/admin/application-deadline/{universityId}")
    ResponseEntity<Object> addApplicationDeadline(@PathVariable int universityId, @Valid @RequestBody ApplicationDeadlineDTO dto){
        return applicationDeadlineService.addApplicationDeadline(universityId, dto);
    }

    @DeleteMapping("/admin/delete-application-deadline/{id}")
    public ResponseEntity<Object> deleteApplicationDeadline(@PathVariable int id) {
        return applicationDeadlineService.deleteApplicationDeadlineById(id);
    }


}
