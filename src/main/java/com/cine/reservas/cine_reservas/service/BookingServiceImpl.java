package com.cine.reservas.cine_reservas.service;

import com.cine.reservas.cine_reservas.model.*;
import com.cine.reservas.cine_reservas.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BillboardRepository billboardRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<BookingEntity> findHorrorMovieBookings(MovieGenreEnum genre, LocalDate startDate, LocalDate endDate) {
        return bookingRepository.findHorrorMovieBookings(genre, startDate, endDate);
    }

    @Override
    @Transactional
    public void disableSeatAndCancelBooking(Long seatId, Long bookingId) {
        SeatEntity seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Butaca no encontrada"));
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Inhabilitar la butaca
        seat.setStatus(false);
        seatRepository.save(seat);

        // Cancelar la reserva
        bookingRepository.delete(booking);

        System.out.println("✅ Butaca inhabilitada y reserva cancelada: ID = " + bookingId);
    }

    @Override
    @Transactional
    public void cancelBillboardAndBookings(Long billboardId) {
        BillboardEntity billboard = billboardRepository.findById(billboardId)
                .orElseThrow(() -> new RuntimeException("Cartelera no encontrada"));

        if (billboard.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("No se puede cancelar funciones de la cartelera con fecha anterior a la actual");
        }

        List<BookingEntity> bookings = bookingRepository.findAllByBillboardId(billboardId);
        bookings.forEach(booking -> {
            SeatEntity seat = booking.getSeat();
            seat.setStatus(true);
            seatRepository.save(seat);
            System.out.println("Cliente afectado: " + booking.getCustomer().getName());
        });

        bookingRepository.deleteAll(bookings);
        billboardRepository.delete(billboard);

        System.out.println("✅ Cartelera y reservas canceladas: ID = " + billboardId);
    }


    @Override
    @Transactional
    public BookingEntity createBooking(Long customerId, Long seatId, Long billboardId) {

        SeatEntity seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Butaca no encontrada"));

        if (!seat.isStatus()) {
            throw new RuntimeException("Butaca ocupada");
        }


        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));


        BillboardEntity billboard = billboardRepository.findById(billboardId)
                .orElseThrow(() -> new RuntimeException("Cartelera no encontrada"));


        boolean alreadyBooked = bookingRepository.existsByCustomerIdAndBillboardId(customerId, billboardId);
        if (alreadyBooked) {
            throw new RuntimeException("El cliente ya tiene una reserva para esta cartelera");
        }

        // Crear la reserva
        BookingEntity booking = new BookingEntity();
        booking.setCustomer(customer);
        booking.setSeat(seat);
        booking.setBillboard(billboard);
        booking.setDate(LocalDate.now());

        // Actualizar el estado de la butaca a ocupada
        seat.setStatus(false);
        seatRepository.save(seat);

        // Guardar la reserva
        return bookingRepository.save(booking);
    }
}
