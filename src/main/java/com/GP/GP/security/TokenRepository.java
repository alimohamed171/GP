package com.GP.GP.security;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


    List<Token> findAllByUserIdAndLoggedOutFalse(Integer userId);


    Optional<Token> findByToken(String token);
}
