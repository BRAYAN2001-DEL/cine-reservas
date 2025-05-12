package com.cine.reservas.cine_reservas.repository;

import com.cine.reservas.cine_reservas.model.SeatEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends BaseRepository<SeatEntity, Long> {
    @Query("SELECT s.room.name, COUNT(s) AS totalSeats, " +
            "SUM(CASE WHEN s.status = true THEN 1 ELSE 0 END) AS availableSeats, " +
            "SUM(CASE WHEN s.status = false THEN 1 ELSE 0 END) AS occupiedSeats " +
            "FROM SeatEntity s " +
            "JOIN s.room r " +
            "JOIN BillboardEntity bb ON bb.room.id = r.id " +
            "WHERE bb.date = CURRENT_DATE " +
            "GROUP BY s.room.name")
    List<Object[]> findSeatAvailabilityByRoom();
    
    @Query("SELECT COUNT(s) FROM SeatEntity s WHERE s.room.id = :roomId")
    int countByRoomId(@Param("roomId") Long roomId);



}