package com.GP.GP.roles.admin.controller;

import com.GP.GP.roles.admin.models.dto.request.MealRequestDTO;
import com.GP.GP.roles.admin.service.contracts.MealService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class MealController {
    @Autowired
    private MealService mealService;

    @PostMapping("admin/edit/add-meals")
    public ResponseEntity<Object> createMeal(@Valid @RequestBody MealRequestDTO mealRequestDTO) {
        return mealService.createMeal(mealRequestDTO);
    }

    @GetMapping("admin/edit/get-meals")
    public ResponseEntity<Object> getAllMeals() {
        return mealService.getAllMeals();
    }

    @DeleteMapping("admin/delete-meal/{mealId}")
    public ResponseEntity<Object> deleteMeal(@PathVariable int mealId) {
        return mealService.deleteMealById(mealId);
    }

    @PutMapping("admin/edit/update-meal/{mealId}")
    public ResponseEntity<Object> updateMeal(
            @PathVariable int mealId,
            @Valid @RequestBody MealRequestDTO mealRequestDTO) {
        return mealService.updateMeal(mealId, mealRequestDTO);
    }
}
