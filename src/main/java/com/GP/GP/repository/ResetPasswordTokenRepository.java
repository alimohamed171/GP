package com.GP.GP.repository;

import com.GP.GP.entities.ResetPasswordToken;
import com.GP.GP.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordTokenRepository extends JpaRepository< ResetPasswordToken, Integer> {
    Optional<ResetPasswordToken> findByToken(String token);

    Optional<ResetPasswordToken> findByUser(User user);

}
