package com.example.demo.controllers;

import com.example.demo.entities.MovieDate;
import com.example.demo.service.MovieDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/dates")
public class MovieDateController {

    @Autowired
    private MovieDateService movieDateService;

    @GetMapping
    public ResponseEntity<?> getMoviesByDate(@RequestParam(required = false) String date) {
        return movieDateService.getMoviesByDate(date);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieDateById(@PathVariable Long id) {
        return movieDateService.getMovieDateById(id);
    }

    @PostMapping
    public ResponseEntity<?> postMovieDate(@RequestBody MovieDate movieDate) {
        return movieDateService.postMovieDate(movieDate);
    }

}
