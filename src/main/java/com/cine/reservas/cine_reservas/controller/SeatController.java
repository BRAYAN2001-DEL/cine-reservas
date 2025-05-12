package com.cine.reservas.cine_reservas.controller;

import com.cine.reservas.cine_reservas.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PutMapping("/enable/{seatId}")
    public String enableSeat(@PathVariable Long seatId) {
        seatService.enableSeat(seatId);
        return "✅ Butaca habilitada.";
    }

    @PutMapping("/disable/{seatId}")
    public String disableSeat(@PathVariable Long seatId) {
        seatService.disableSeat(seatId);
        return "✅ Butaca deshabilitada.";
    }
}