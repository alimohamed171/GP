package com.GP.GP.repository;

import com.GP.GP.entities.Building;
import com.GP.GP.entities.University;
import com.GP.GP.utill.Enums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    List<Building> findByUniversity(University university);

    Optional<Building> findByType(Enums.BuildingType buildingType);
}
