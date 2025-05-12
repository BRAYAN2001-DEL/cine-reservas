package com.cine.reservas.cine_reservas.repository;

import com.cine.reservas.cine_reservas.model.BookingEntity;
import com.cine.reservas.cine_reservas.model.MovieGenreEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface BookingRepository extends BaseRepository<BookingEntity, Long> {

    @Query("SELECT b FROM BookingEntity b " +
            "JOIN b.billboard bb " +
            "JOIN bb.movie m " +
            "WHERE m.genre = :genre " +
            "AND b.date BETWEEN :startDate AND :endDate")
    List<BookingEntity> findHorrorMovieBookings(
            @Param("genre") MovieGenreEnum genre,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM BookingEntity b WHERE b.billboard.id = :billboardId")
    List<BookingEntity> findAllByBillboardId(@Param("billboardId") Long billboardId);

    @Query("SELECT COUNT(b) FROM BookingEntity b WHERE b.seat.room.id = :roomId AND b.date = :date")
    int countOccupiedSeatsByRoomIdAndDate(@Param("roomId") Long roomId, @Param("date") LocalDate date);


    boolean existsByCustomerIdAndBillboardId(@Param("customerId")  Long customerId, @Param("billboardId")  Long billboardId);

}