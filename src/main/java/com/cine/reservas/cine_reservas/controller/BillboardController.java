package com.cine.reservas.cine_reservas.controller;

import com.cine.reservas.cine_reservas.service.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billboards")
public class BillboardController {

    @Autowired
    private BillboardService billboardService;

    @DeleteMapping("/cancel/{billboardId}")
    public String cancelBillboard(@PathVariable Long billboardId) {
        try {
            billboardService.cancelBillboard(billboardId);
            return "✅ Cartelera cancelada y reservas anuladas.";
        } catch (IllegalArgumentException e) {
            return "❌ " + e.getMessage();
        }
    }

    @GetMapping("/seats/availability/{roomId}")
    public String getSeatsAvailability(@PathVariable Long roomId) {
        var availability = billboardService.getSeatsAvailability(roomId);
        return String.format("💺 Disponibles: %d - 🚫 Ocupadas: %d", availability.getAvailable(), availability.getOccupied());
    }
}
