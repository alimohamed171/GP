package com.GP.GP.roles.admin.models.dto.request;

import com.GP.GP.utill.Enums;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealRequestDTO {
    @NotNull(message = "Meal date is required")
    @FutureOrPresent(message = "Meal date cannot be in the past")
    private LocalDate mealDate;

    @NotNull(message = "Meal type is required")
    private Enums.MealType mealType;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Image URL is required")
    private String image;
}
