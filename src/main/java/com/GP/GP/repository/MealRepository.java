package com.GP.GP.repository;

import com.GP.GP.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Integer> {
}
