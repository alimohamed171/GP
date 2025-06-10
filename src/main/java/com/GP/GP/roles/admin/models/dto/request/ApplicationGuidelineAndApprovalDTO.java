package com.GP.GP.roles.admin.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationGuidelineAndApprovalDTO {
    @NotBlank(message = "Guidelines cannot be empty")
    private String guidelines;
    private MultipartFile media;
}
