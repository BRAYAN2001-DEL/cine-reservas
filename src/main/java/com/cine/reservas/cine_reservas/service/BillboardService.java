package com.cine.reservas.cine_reservas.service;

import com.cine.reservas.cine_reservas.repository.BillboardRepository;
import com.cine.reservas.cine_reservas.repository.BookingRepository;
import com.cine.reservas.cine_reservas.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BillboardService {

    @Autowired
    private BillboardRepository billboardRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Transactional
    public void cancelBillboard(Long billboardId) {
        var billboard = billboardRepository.findById(billboardId)
                .orElseThrow(() -> new RuntimeException("Cartelera no encontrada"));

        // Verificar si la función es anterior a la fecha actual
        if (billboard.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("No se puede cancelar funciones de la cartelera con fecha anterior a la actual");
        }

        // Obtener todas las reservas de la cartelera
        var bookings = bookingRepository.findAllByBillboardId(billboardId);

        System.out.println("Clientes afectados por la cancelación de la cartelera:");
        bookings.forEach(booking -> {
            System.out.println("Cliente: " + booking.getCustomer().getName() + " " + booking.getCustomer().getLastname());
            // Habilitar la butaca
            var seat = booking.getSeat();
            seat.setStatus(true);
            seatRepository.save(seat);
            // Cancelar la reserva
            bookingRepository.delete(booking);
        });

        // Eliminar la cartelera
        billboardRepository.delete(billboard);
        System.out.println("Cartelera cancelada y reservas eliminadas.");
    }

    public SeatAvailability getSeatsAvailability(Long roomId) {
        int totalSeats = seatRepository.countByRoomId(roomId);
        int occupiedSeats = bookingRepository.countOccupiedSeatsByRoomIdAndDate(roomId, LocalDate.now());
        int availableSeats = totalSeats - occupiedSeats;
        return new SeatAvailability(availableSeats, occupiedSeats);
    }

    public static class SeatAvailability {
        private int available;
        private int occupied;

        public SeatAvailability(int available, int occupied) {
            this.available = available;
            this.occupied = occupied;
        }

        public int getAvailable() {
            return available;
        }

        public int getOccupied() {
            return occupied;
        }
    }
}
