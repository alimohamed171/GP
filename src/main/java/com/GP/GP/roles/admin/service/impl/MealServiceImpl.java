package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.Meal;
import com.GP.GP.entities.User;
import com.GP.GP.repository.MealRepository;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.admin.models.dto.request.MealRequestDTO;
import com.GP.GP.roles.admin.models.dto.response.MealAssignmentResponseDTO;
import com.GP.GP.roles.admin.models.dto.response.MealResponseDTO;
import com.GP.GP.roles.admin.models.mapper.MealMapper;
import com.GP.GP.roles.admin.service.contracts.MealService;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public ResponseEntity<Object> assignMealToStudent(int studentId, int mealId) {
        Optional<User> optionalStudent = userRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Student not found"), HttpStatus.NOT_FOUND);
        }

        User student = optionalStudent.get();

        if (student.getStatus() != Enums.AdmissionRequestStatues.ACCEPTED) {
            return new ResponseEntity<>(new BaseResponse(false, "Student is not accepted and cannot be assigned a meal"), HttpStatus.FORBIDDEN);
        }

        Optional<Meal> optionalMeal = mealRepository.findById(mealId);
        if (optionalMeal.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Meal not found"), HttpStatus.NOT_FOUND);
        }

        Meal meal = optionalMeal.get();

        List<Meal> meals = new ArrayList<>();
        meals.add(meal);
        student.setMeals(meals);
        userRepository.save(student);

        MealAssignmentResponseDTO responseDTO = MealMapper.mapToMealResponse(meal, student);

        return new ResponseEntity<>(new BaseResponse(true, "Meal assigned successfully", responseDTO), HttpStatus.OK);
    }
}
