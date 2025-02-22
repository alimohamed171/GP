package com.GP.GP.repository;

import com.GP.GP.entities.Building;
import com.GP.GP.entities.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    List<Building> findByUniversity(University university);
}
