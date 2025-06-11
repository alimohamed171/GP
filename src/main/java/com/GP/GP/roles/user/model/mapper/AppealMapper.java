package com.GP.GP.roles.user.model.mapper;

import com.GP.GP.entities.Appeal;
import com.GP.GP.entities.Complaint;
import com.GP.GP.roles.user.model.request.AppealRequestDTO;
import com.GP.GP.roles.user.model.request.ComplaintRequestDTO;
import com.GP.GP.roles.user.model.response.AppealResponseDTO;
import com.GP.GP.roles.user.model.response.ComplaintResponseDTO;
import lombok.Builder;

@Builder
public class AppealMapper {
    public static Appeal requestToEntity(AppealRequestDTO request) {
        if (request == null) return null;
        Appeal appeal = new Appeal();
        appeal.setReason(request.getReason());
        return appeal;
    }
    public static AppealResponseDTO entityToResponse(Appeal appeal) {
        if (appeal == null) return null;

        AppealResponseDTO response = new AppealResponseDTO();
        response.setId(appeal.getId());
        response.setReason(appeal.getReason());
        response.setStatus(appeal.getStatus());
        if (appeal.getUser() != null) {
            response.setUserId(appeal.getUser().getId());
            response.setUsername(appeal.getUser().getUsername());
        }
        return response;
    }
}
