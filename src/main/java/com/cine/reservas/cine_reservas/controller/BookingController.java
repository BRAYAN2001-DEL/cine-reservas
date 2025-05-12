package com.cine.reservas.cine_reservas.controller;

import com.cine.reservas.cine_reservas.dto.BookingDTO;
import com.cine.reservas.cine_reservas.model.MovieGenreEnum;
import com.cine.reservas.cine_reservas.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Obtener reservas de películas de terror en un rango de fechas
    @GetMapping("/horror")
    public List<BookingDTO> getHorrorMovieBookings(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        var results = bookingService.findHorrorMovieBookings(
                MovieGenreEnum.HORROR,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
        return results.stream()
                .map(booking -> new BookingDTO(
                        booking.getId(),
                        booking.getCustomer().getName(),
                        booking.getBillboard().getMovie().getName(),
                        booking.getBillboard().getRoom().getName(),
                        booking.getDate().toString()
                )).toList();
    }

    // Cancelar reserva y deshabilitar butaca
    @PostMapping("/cancel/{bookingId}/seat/{seatId}")
    public String cancelBookingAndDisableSeat(
            @PathVariable Long bookingId,
            @PathVariable Long seatId) {
        bookingService.disableSeatAndCancelBooking(seatId, bookingId);
        return "✅ Reserva cancelada y butaca inhabilitada.";
    }

    // Cancelar cartelera y todas sus reservas
    @PostMapping("/cancel-billboard/{billboardId}")
    public String cancelBillboardAndBookings(@PathVariable Long billboardId) {
        bookingService.cancelBillboardAndBookings(billboardId);
        return "✅ Cartelera cancelada y reservas eliminadas.";
    }

    @PostMapping("/reserve")
    public BookingDTO reserveSeat(@RequestParam Long customerId,
                                  @RequestParam Long seatId,
                                  @RequestParam Long billboardId) {
        var booking = bookingService.createBooking(customerId, seatId, billboardId);
        return new BookingDTO(
                booking.getId(),
                booking.getCustomer().getName(),
                booking.getBillboard().getMovie().getName(),
                booking.getBillboard().getRoom().getName(),
                booking.getDate().toString()
        );
    }

}
