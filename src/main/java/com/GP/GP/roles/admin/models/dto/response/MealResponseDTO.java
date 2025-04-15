package com.GP.GP.roles.admin.models.dto.response;

import com.GP.GP.entities.Meal;
import com.GP.GP.utill.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealResponseDTO {
    private Integer id;
    private LocalDate mealDate;
    private Enums.MealType mealType;
    private String description;
    private String image;
}
