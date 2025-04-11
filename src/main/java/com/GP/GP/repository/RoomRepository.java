package com.GP.GP.repository;


import com.GP.GP.entities.Room;
import com.GP.GP.utill.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByBuildingId(int buildingId);
    @Query("SELECT r FROM Room r " +
            "WHERE r.status = com.GP.GP.utill.Enums.RoomStatus.AVAILABLE " +
            "AND r.currentOccupancy < r.capacity " +
            "AND r.building.type = :buildingType " +
            "AND r.type = :roomType")
    List<Room> findAvailableRoomsByGender(@Param("buildingType") Enums.BuildingType buildingType,
                                          @Param("roomType") Enums.RoomType roomType);
}
