package com.GP.GP.roles.admin.models.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PenaltyResponseDTO {
    private Integer id;
    private String penaltyTitle ;
    private String reason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateIssued;
    private UserDTO user;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserDTO {
        private Integer userId;
        private String firstName;
        private String lastName;
        private String username;
    }
}

