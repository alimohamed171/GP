package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.Penalty;
import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.request.PenaltyDTO;
import com.GP.GP.roles.admin.models.dto.response.PenaltyResponseDTO;
import lombok.Builder;

@Builder
public class PenaltyMapper {
    public static Penalty toEntity(PenaltyDTO dto, User user) {
        return Penalty.builder()
                .penaltyTitle(dto.getPenaltyTitle())
                .reason(dto.getReason())
                .dateIssued(dto.getDateIssued().atStartOfDay())
                .user(user)
                .build();
    }

    public static PenaltyResponseDTO toDTO(Penalty penalty) {
        return PenaltyResponseDTO.builder()
                .id(penalty.getId())
                .penaltyTitle(penalty.getPenaltyTitle())
                .reason(penalty.getReason())
                .dateIssued(penalty.getDateIssued())
                .user(PenaltyResponseDTO.UserDTO.builder()
                        .userId(penalty.getUser().getId())
                        .firstName(penalty.getUser().getFirstName())
                        .lastName(penalty.getUser().getLastName())
                        .username(penalty.getUser().getUsername())
                        .build())
                .build();
    }
}
