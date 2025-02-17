package com.GP.GP.roles.user.model.mapper;

import com.GP.GP.entities.Complaint;
import com.GP.GP.roles.user.model.request.ComplaintRequestDTO;
import com.GP.GP.roles.user.model.response.ComplaintResponseDTO;
import lombok.Builder;

@Builder

public class ComplaintMapper {
    public static Complaint requestToEntity(ComplaintRequestDTO request) {
        if (request == null) return null;
        Complaint complaint = new Complaint();
        complaint.setComplaintText(request.getComplaintText());
        return complaint;
    }
    public static ComplaintResponseDTO entityToResponse(Complaint complaint) {
        if (complaint == null) return null;

        ComplaintResponseDTO response = new ComplaintResponseDTO();
        response.setId(complaint.getId());
        response.setComplaintText(complaint.getComplaintText());
        response.setCreatedAt(complaint.getCreatedAt());

        if (complaint.getUser() != null) {
            response.setUserId(complaint.getUser().getId());
            response.setUsername(complaint.getUser().getUsername());
        }

        return response;
    }

}
