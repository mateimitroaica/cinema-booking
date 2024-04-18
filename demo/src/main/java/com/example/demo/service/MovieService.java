package com.example.demo.service;

import com.example.demo.entities.Movie;
import com.example.demo.entities.MovieDate;
import com.example.demo.entities.MoviePlayingHours;
import com.example.demo.entities.Seat;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieDateRepository movieDateRepository;

    @Autowired
    private MoviePlayingHoursRepository moviePlayingHoursRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public ResponseEntity<?> getAllMovies() {
        ResponseEntity<?> response = null;

        List<Movie> movies = movieRepository.findAll();

        if (movies.isEmpty()) {
            response = new ResponseEntity<>("No content available.", HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(movies, HttpStatus.OK);
        }

        return response;
    }

    public ResponseEntity<?> getMovieById(Long id) {
        ResponseEntity<?> response = null;

        Optional<Movie> optional = movieRepository.findById(id);

        if (optional.isPresent()) {
            response = new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    public ResponseEntity<?> postMovie(Movie movie) {
        try {
            MovieDate movieDate = movie.getMovieDate();

            // Handle MovieDate
            if (movieDate != null) {
                if (movieDate.getId() != null) {
                    movieDate = movieDateRepository.findById(movieDate.getId())
                            .orElseThrow(() -> new RuntimeException("Date not found."));
                } else if (movieDate.getDate() != null) {
                    List<MovieDate> existingMovieDates = movieDateRepository.findByDate(movieDate.getDate());
                    if (!existingMovieDates.isEmpty()) {
                        movieDate = existingMovieDates.get(0);
                    } else {
                        movieDate = movieDateRepository.saveAndFlush(movieDate);
                    }
                    movie.setMovieDate(movieDate);
                } else {
                    throw new RuntimeException("Invalid movie date provided");
                }
            }

            // Save the movie
            Movie insertedMovie = movieRepository.saveAndFlush(movie);

            // Associate MoviePlayingHours with the movie and save seats
            for (MoviePlayingHours playingHours : movie.getStartingHours()) {
                playingHours.setMovie(insertedMovie);
                MoviePlayingHours savedPlayingHours = moviePlayingHoursRepository.saveAndFlush(playingHours);

                for (int i = 0; i < playingHours.getRoomCapacity(); i++) {
                    Seat seat = new Seat();
                    seat.setSeatAvailable(true);
                    seat.setRoomDetails(savedPlayingHours);
                    seatRepository.saveAndFlush(seat);
                }
            }

            // Update MovieDate if necessary
            if (movieDate != null && !movieDate.getMovies().contains(insertedMovie)) {
                movieDate.getMovies().add(insertedMovie);
                movieDateRepository.saveAndFlush(movieDate);
            }

            return new ResponseEntity<>(insertedMovie, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void cleanUpEmptyDates() {
        List<MovieDate> emptyDates = new ArrayList<>();
        for (var date: movieDateRepository.findAll()) {
            if (date.getMovies().isEmpty()) {
                emptyDates.add(date);
            }
        }

        for (MovieDate emptyDate: emptyDates) {
            movieDateRepository.delete(emptyDate);
        }
    }

    @Transactional
    public ResponseEntity<?> updateMovie(Long id, Movie updatedMovie) {

        try {
            Optional<Movie> optionalMovie = movieRepository.findById(id);
            if (optionalMovie.isPresent()) {
                Movie existingMovie = optionalMovie.get();

                existingMovie.setTitle(updatedMovie.getTitle());
                existingMovie.setDescription(updatedMovie.getDescription());
                existingMovie.setDuration(updatedMovie.getDuration());
                existingMovie.setYear(updatedMovie.getYear());
                existingMovie.setAgeRestriction(updatedMovie.getAgeRestriction());
                existingMovie.setDirector(updatedMovie.getDirector());
                existingMovie.setImgUrl(updatedMovie.getImgUrl());
                existingMovie.setTrailerUrl(updatedMovie.getTrailerUrl());

                MovieDate updatedMovieDate = updatedMovie.getMovieDate();
                List<MovieDate> existingMovieDates = movieDateRepository.findByDate(updatedMovieDate.getDate());
                MovieDate selectedMovieDate = null;

                if (!existingMovieDates.isEmpty()) {
                    selectedMovieDate = existingMovieDates.get(0);
                } else {
                    selectedMovieDate = movieDateRepository.save(updatedMovieDate);
                }

                existingMovie.setMovieDate(selectedMovieDate);

                Movie savedMovie = movieRepository.saveAndFlush(existingMovie);

                cleanUpEmptyDates();

                return new ResponseEntity<>(savedMovie, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteMovieById(Long id) {
        try{
            Optional<Movie> optionalMovie = movieRepository.findById(id);

            if (optionalMovie.isEmpty()) {
                throw new RuntimeException("Movie not found." + id);
            }

            Movie movie = optionalMovie.get();

            movie.setMovieDate(null);
            movieRepository.saveAndFlush(movie);

            for (var playingHours: movie.getStartingHours()) {
                for (Seat seat: playingHours.getSeats()) {
                    if (seat.getTicket() != null) {
                        ticketRepository.delete(seat.getTicket());
                    }
                }
            }

            for (var playingHours: movie.getStartingHours()) {
                seatRepository.deleteAll(playingHours.getSeats());
            }

            moviePlayingHoursRepository.deleteAll(movie.getStartingHours());
            movieRepository.delete(movie);

            cleanUpEmptyDates();

            return new ResponseEntity<>("Movie successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
