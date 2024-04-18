package com.example.demo.repository;

import com.example.demo.entities.Movie;
import com.example.demo.entities.MovieDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitle(String title);

    List<Movie> findByMovieDate(MovieDate movieDate);
}
