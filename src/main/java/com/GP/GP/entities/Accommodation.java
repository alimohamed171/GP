package com.GP.GP.entities;

import com.GP.GP.utill.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "admission_request_id")
    private AdmissionRequest admissionRequest;

    @OneToOne
    @JoinColumn(name = "student_profile_id")
    private StudentProfile studentProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "housing_type_id")
    private HousingType housingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private Enums.AccommodationStatus status;
}
/*
User (1:1) → AdmissionRequest (1:1) → Accommodation (1:1)→StudentProfile
User (1:1) → StudentProfile
Accommodation (M:1) → Room
Accommodation (M:1) → HousingType
*/
