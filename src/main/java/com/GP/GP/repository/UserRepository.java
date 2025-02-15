package com.GP.GP.repository;

import com.GP.GP.entities.User;
import com.GP.GP.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    List<User> findByRoleNot(Role role);
}
