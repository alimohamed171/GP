package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealAssignmentResponseDTO {
    private Integer mealId;
    private LocalDate mealDate;
    private Enums.MealType mealType;
    private String description;
    private String image;
    private Integer studentId;
    private String studentName;
    private String studentEmail;
    private String studentPhoneNumber;
    private Enums.Gender studentGender;
    private String faculty;
    private String level;
}
