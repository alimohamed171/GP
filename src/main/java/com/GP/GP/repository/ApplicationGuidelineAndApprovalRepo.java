package com.GP.GP.repository;

import com.GP.GP.entities.ApplicationGuidelineAndApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationGuidelineAndApprovalRepo extends JpaRepository<ApplicationGuidelineAndApproval, Integer> {

}
