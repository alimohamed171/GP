package com.GP.GP.repository;


import com.GP.GP.entities.Room;
import com.GP.GP.utill.Enums;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByBuildingId(int buildingId);

    @Query("""
            SELECT r
            FROM Room r
            WHERE r.building.id = :buildingId
              AND r.type = :roomType
              AND r.status = 'AVAILABLE'
              AND r.currentOccupancy < r.capacity
              AND r.occupiedBeds < r.bedCount""")
    List<Room> findAvailableRoomsByBuildingAndRoomType(
            @Param("buildingId") int buildingId,
            @Param("roomType") Enums.RoomType roomType);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT r
            FROM Room r
            JOIN r.building b
            WHERE b.type = :buildingType
              AND r.type = :roomType
              AND r.status = 'AVAILABLE'
              AND r.currentOccupancy < r.capacity""")
    List<Room> findAvailableRoomsByBuildingTypeAndRoomType(
            @Param("buildingType") Enums.BuildingType buildingType,
            @Param("roomType") Enums.RoomType roomType);

    boolean existsByRoomNumberAndBuildingId(String roomNumber, Integer buildingId);
}

