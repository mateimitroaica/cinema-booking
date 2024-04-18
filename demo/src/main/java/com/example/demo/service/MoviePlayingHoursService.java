package com.example.demo.service;

import com.example.demo.entities.MovieDate;
import com.example.demo.entities.MoviePlayingHours;
import com.example.demo.repository.MoviePlayingHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoviePlayingHoursService {

    @Autowired
    private MoviePlayingHoursRepository moviePlayingHoursRepository;

    public ResponseEntity<?> getStartingHour(Long id) {
        ResponseEntity<?> response = null;

        Optional<MoviePlayingHours> optional = moviePlayingHoursRepository.findById(id);

        if (optional.isPresent()) {
            response = new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


    public ResponseEntity<?> postStartingHour(MoviePlayingHours movie) {
        ResponseEntity<?> response = null;

        try{
            MoviePlayingHours insertedHour = moviePlayingHoursRepository.saveAndFlush(movie);
            response = new ResponseEntity<>(insertedHour, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;

    }
}
