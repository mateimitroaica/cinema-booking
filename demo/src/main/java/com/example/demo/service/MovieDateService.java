package com.example.demo.service;

import com.example.demo.entities.Movie;
import com.example.demo.entities.MovieDate;
import com.example.demo.repository.MovieDateRepository;
import com.example.demo.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovieDateService {
    @Autowired
    private MovieDateRepository movieDateRepository;

    @Autowired
    private MovieRepository movieRepository;

    public ResponseEntity<?> getMovieDateById(Long id) {
        ResponseEntity<?> response = null;

        Optional<MovieDate> optional = movieDateRepository.findById(id);

        if (optional.isPresent()) {
            response = new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;

    }

    public ResponseEntity<?> postMovieDate(MovieDate movieDate) {
        ResponseEntity<?> response = null;

        try{
            MovieDate insertedDate = movieDateRepository.saveAndFlush(movieDate);
            response = new ResponseEntity<>(insertedDate, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<?> getMoviesByDate(String date) {
        try{
            Date dateFormat = null;
            if (date != null && !date.isEmpty()) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try{
                    dateFormat = simpleDateFormat.parse(date);
                } catch (ParseException e) {
                    return new ResponseEntity<>("Invalid date format", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            if (dateFormat != null) {
                List<MovieDate> movieDates = movieDateRepository.findByDate(dateFormat);
                if (movieDates.isEmpty()) {
                    return new ResponseEntity<>("No movies found for the given date", HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(movieDates, HttpStatus.OK);
            } else {
                List<MovieDate> allMovieDates = movieDateRepository.findAll();
                return new ResponseEntity<>(allMovieDates, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
