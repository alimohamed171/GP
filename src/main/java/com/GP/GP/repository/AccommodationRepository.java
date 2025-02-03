package com.GP.GP.repository;

import com.GP.GP.entities.Accommodation;
import com.GP.GP.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,Integer> {
}
