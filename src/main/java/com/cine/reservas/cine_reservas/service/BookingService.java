package com.cine.reservas.cine_reservas.service;

import com.cine.reservas.cine_reservas.model.BookingEntity;
import com.cine.reservas.cine_reservas.model.MovieGenreEnum;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    List<BookingEntity> findHorrorMovieBookings(MovieGenreEnum genre, LocalDate startDate, LocalDate endDate);

    void disableSeatAndCancelBooking(Long seatId, Long bookingId);

    void cancelBillboardAndBookings(Long billboardId);

    BookingEntity createBooking(Long customerId, Long seatId, Long billboardId);


}
