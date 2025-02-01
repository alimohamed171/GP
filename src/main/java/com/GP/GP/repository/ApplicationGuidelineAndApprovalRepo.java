package com.GP.GP.repository;

import com.GP.GP.entities.ApplicationGuidelineAndApproval;
import com.GP.GP.entities.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationGuidelineAndApprovalRepo extends JpaRepository<ApplicationGuidelineAndApproval, Integer> {
    List<ApplicationGuidelineAndApproval> findByUniversity(University university);

}
