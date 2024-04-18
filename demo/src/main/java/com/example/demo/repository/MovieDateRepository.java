package com.example.demo.repository;

import com.example.demo.entities.Movie;
import com.example.demo.entities.MovieDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovieDateRepository extends JpaRepository<MovieDate, Long> {
    List<MovieDate> findByDate(Date date);
}
