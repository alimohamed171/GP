package com.GP.GP.repository;

import com.GP.GP.entities.ApplicationDeadline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationDeadlineRepository extends JpaRepository<ApplicationDeadline, Integer> {
    List<ApplicationDeadline> findAllByUniversityId(int universityId);
}
