package com.GP.GP.repository;

import com.GP.GP.entities.Appeal;
import com.GP.GP.entities.User;
import com.GP.GP.utill.Enums;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppealRepository extends JpaRepository<Appeal, Integer> {
    boolean existsByUserAndStatus(User user, Enums.AdmissionRequestStatues status);
}
