package com.GP.GP.roles.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintResponseDTO {
    private Integer id;
    private String complaintText;
    private LocalDateTime createdAt;
    private Integer userId;
    private String username;
}
