package com.GP.GP.repository;

import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.entities.User;
import com.GP.GP.security.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    List<User> findByRoleNot(Role role);
    List<User> findByRoleNotIn(List<Role> roles);
    Optional<User> findByNationalId(String nationalId);
    List<User> findByRoomId(int roomId);
    List<User> findByRole(Role role);
    Optional<User> findByUsernameAndNationalId(String username, String nationalId);

    @Query("SELECT u FROM User u WHERE u.username LIKE :username AND u.nationalId LIKE :nationalId")
    Page<User> findByUsernameLikeAndNationalIdLike(@Param("username") String username, @Param("nationalId") String nationalId, Pageable pageable);
}
