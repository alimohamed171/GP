package com.GP.GP.entities;

import com.GP.GP.utill.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "room_number", nullable = false, length = 10)
    private String roomNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "current_occupancy")
    private Integer currentOccupancy = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Enums.RoomType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Enums.RoomStatus status = Enums.RoomStatus.AVAILABLE;

    @OneToMany(mappedBy = "room")
    private List<Accommodation> accommodations = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    // Getters and Setters
}
