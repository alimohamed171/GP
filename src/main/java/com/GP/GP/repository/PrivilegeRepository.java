package com.GP.GP.repository;

import com.GP.GP.security.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    List<Privilege> findAllById(Integer ids); // Use Integer for privilege IDs

    List<Privilege> findByUsersId(Integer userId);
}
