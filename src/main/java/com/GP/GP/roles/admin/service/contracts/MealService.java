package com.GP.GP.roles.admin.service.contracts;

import com.GP.GP.roles.admin.models.dto.request.MealRequestDTO;
import org.springframework.http.ResponseEntity;

public interface MealService {
    ResponseEntity<Object> createMeal(MealRequestDTO requestDTO);
    ResponseEntity<Object> getAllMeals();
    ResponseEntity<Object> deleteMealById(int mealId);
    ResponseEntity<Object> updateMeal(int mealId,MealRequestDTO requestDTO);
    ResponseEntity<Object> assignMealToStudent(int studentId, int mealId);
}
