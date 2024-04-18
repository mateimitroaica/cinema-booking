package com.example.demo.controllers;

import com.example.demo.entities.MoviePlayingHours;
import com.example.demo.service.MoviePlayingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playingHours")

public class MoviePlayingHoursController {

    @Autowired
    private MoviePlayingHoursService moviePlayingHoursService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getStartingHour(@PathVariable Long id) {
        return moviePlayingHoursService.getStartingHour(id);
    }

    @PostMapping
    public ResponseEntity<?> postStartingHour(@RequestBody MoviePlayingHours movie) {
        return moviePlayingHoursService.postStartingHour(movie);
    }
}
