package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.Meal;
import com.GP.GP.repository.MealRepository;
import com.GP.GP.roles.admin.models.dto.request.MealRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.MealResponseDTO;
import com.GP.GP.roles.admin.models.mapper.MealMapper;
import com.GP.GP.roles.admin.service.contracts.MealService;
import com.GP.GP.utill.base.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Override
    public ResponseEntity<Object> createMeal(@Valid MealRequestDTO requestDTO) {
        Meal meal = MealMapper.mapToRequestEntity(requestDTO);
        Meal savedMeal = mealRepository.save(meal);
        MealResponseDTO responseDTO = MealMapper.mapToResponseEntity(savedMeal);

        BaseResponse response = new BaseResponse(true, "Meal created successfully.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getAllMeals() {
        List<Meal> meals = mealRepository.findAll();

        if (meals.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "No meals found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<MealResponseDTO> responseDTOs = meals.stream()
                .map(MealMapper::mapToResponseEntity)
                .toList();

        BaseResponse response = new BaseResponse(true, "Meals retrieved successfully.", responseDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteMealById(int mealId) {
        Optional<Meal> mealOptional = mealRepository.findById(mealId);

        if (mealOptional.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "Meal not found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        mealRepository.delete(mealOptional.get());
        BaseResponse response = new BaseResponse(true, "Meal deleted successfully.", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateMeal(int mealId, @Valid MealRequestDTO requestDTO) {
        Optional<Meal> mealOptional = mealRepository.findById(mealId);

        if (mealOptional.isEmpty()) {
            BaseResponse response = new BaseResponse(false, "Meal not found.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Meal meal = mealOptional.get();

        MealMapper.updateMealEntity(meal, requestDTO);
        mealRepository.save(meal);

        MealResponseDTO responseDTO = MealMapper.mapToResponseEntity(meal);
        BaseResponse response = new BaseResponse(true, "Meal updated successfully.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
