package com.GP.GP.repository;

import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.entities.User;
import com.GP.GP.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    List<User> findByRoleNot(Role role);
    Optional<User> findByNationalId(String nationalId);
    List<User> findByRoomId(int roomId);

}
