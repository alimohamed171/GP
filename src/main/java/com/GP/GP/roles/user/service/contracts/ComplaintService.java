package com.GP.GP.roles.user.service.contracts;

import com.GP.GP.entities.User;
import com.GP.GP.roles.user.model.request.ComplaintRequestDTO;
import org.springframework.http.ResponseEntity;

public interface ComplaintService {
    ResponseEntity<Object> createComplaint(ComplaintRequestDTO request,int userId );
    ResponseEntity<Object> getAllComplaints();
    ResponseEntity<Object> getComplaintById(int id);
    ResponseEntity<Object>updateComplaint(int id, ComplaintRequestDTO request);
    ResponseEntity<Object> deleteComplaint(int id);
    ResponseEntity<Object>  getComplaintsByUser(int userId);
}
