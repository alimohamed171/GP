package com.GP.GP.roles.user.service.contracts;

import com.GP.GP.roles.user.model.request.AppealRequestDTO;
import com.GP.GP.utill.Enums;
import org.springframework.http.ResponseEntity;

public interface AppealService {
    ResponseEntity<Object> submitAppealRequest(Integer userId, AppealRequestDTO dto);
    ResponseEntity<Object> getAllAppeals();
    ResponseEntity<Object> getAppealById(Integer id);
    ResponseEntity<Object> updateAppealStatus(int id, Enums.AdmissionRequestStatues status);
    ResponseEntity<Object> deleteAppeal(int id);
    ResponseEntity<Object> getAppealsByUser(int userId);
    ResponseEntity<Object>getAppealStatus(int userId);

}
