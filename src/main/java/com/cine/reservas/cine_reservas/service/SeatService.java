package com.cine.reservas.cine_reservas.service;

import com.cine.reservas.cine_reservas.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public void enableSeat(Long seatId) {
        var seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Butaca no encontrada"));
        seat.setStatus(true);
        seatRepository.save(seat);
    }

    public void disableSeat(Long seatId) {
        var seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Butaca no encontrada"));
        seat.setStatus(false);
        seatRepository.save(seat);
    }
}