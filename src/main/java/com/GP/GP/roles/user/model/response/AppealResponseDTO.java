package com.GP.GP.roles.user.model.response;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppealResponseDTO {
        private Integer id;
        private String reason;
        private Enums.AdmissionRequestStatues status;
        private Integer userId;
        private String username;

}
