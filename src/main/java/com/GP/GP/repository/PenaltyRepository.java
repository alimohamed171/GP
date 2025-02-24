package com.GP.GP.repository;


import com.GP.GP.entities.Penalty;
import com.GP.GP.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Integer> {
    List<Penalty> findByUser(User user);
    List<Penalty> findByUserId(Integer userId);

}
