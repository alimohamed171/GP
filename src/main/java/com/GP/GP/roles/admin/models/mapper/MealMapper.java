package com.GP.GP.roles.admin.models.mapper;

import com.GP.GP.entities.Meal;
import com.GP.GP.entities.User;
import com.GP.GP.roles.admin.models.dto.request.MealRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.MealAssignmentResponseDTO;
import com.GP.GP.roles.admin.models.dto.response.MealResponseDTO;

public class MealMapper {

    public static MealResponseDTO mapToResponseEntity(Meal meal) {
        return MealResponseDTO.builder()
                .id(meal.getId())
                .mealDate(meal.getMealDate())
                .mealType(meal.getMealType())
                .description(meal.getDescription())
                .image(meal.getImage())
                .build();
    }

    public static Meal mapToRequestEntity(MealRequestDTO dto) {
        return Meal.builder()
                .mealDate(dto.getMealDate())
                .mealType(dto.getMealType())
                .description(dto.getDescription())
                .image(dto.getImage())
                .build();
    }

    public static void updateMealEntity(Meal meal, MealRequestDTO dto) {
        meal.setMealDate(dto.getMealDate());
        meal.setMealType(dto.getMealType());
        meal.setDescription(dto.getDescription());
        meal.setImage(dto.getImage());
    }

    public static MealAssignmentResponseDTO mapToMealResponse(Meal meal, User student) {
        return MealAssignmentResponseDTO.builder()
                .mealId(meal.getId())
                .mealDate(meal.getMealDate())
                .mealType(meal.getMealType())
                .description(meal.getDescription())
                .image(meal.getImage())
                .studentId(student.getId())
                .studentName(student.getFirstName() + " " + student.getLastName())
                .studentEmail(student.getUsername())
                .studentPhoneNumber(student.getMobileNumber())
                .studentGender(student.getGender())
                .faculty(student.getFaculty())
                .level(student.getLevel())
                .build();
    }
}
