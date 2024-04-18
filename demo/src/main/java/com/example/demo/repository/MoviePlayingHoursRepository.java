package com.example.demo.repository;

import com.example.demo.entities.MoviePlayingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviePlayingHoursRepository extends JpaRepository<MoviePlayingHours, Long> {
}
