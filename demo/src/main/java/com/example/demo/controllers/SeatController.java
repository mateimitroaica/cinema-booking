package com.example.demo.controllers;

import com.example.demo.entities.Seat;
import com.example.demo.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getSeatById(@PathVariable Long id) {
        return seatService.getSeatById(id);
    }

    @PostMapping("/buy-ticket/{id}")
    public ResponseEntity<?> buyTicket(@PathVariable Long id) {
        return seatService.buyTicket(id);
    }
}
