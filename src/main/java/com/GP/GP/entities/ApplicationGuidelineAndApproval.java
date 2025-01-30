package com.GP.GP.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "application_guidelines_and_approvals")
public class ApplicationGuidelineAndApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "guidelines",columnDefinition = "TEXT")
    private String guidelines;

    @OneToOne
    @JoinColumn(name = "university_id", nullable = false, unique = true)
    private University university;

}
