package com.GP.GP.repository;

import com.GP.GP.entities.AdmissionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmissionRequestRepository extends JpaRepository<AdmissionRequest, Integer> {
    @Query("SELECT ar FROM AdmissionRequest ar WHERE ar.id = :requestId AND ar.user.id = :userId")
    Optional<AdmissionRequest> findByIdAndUserId(
            @Param("requestId") Integer requestId,
            @Param("userId") Integer userId);

    @Query("SELECT ar FROM AdmissionRequest ar WHERE ar.user.id = :userId")
    Optional<AdmissionRequest> findByUserId(
            @Param("userId") Integer userId);
}
