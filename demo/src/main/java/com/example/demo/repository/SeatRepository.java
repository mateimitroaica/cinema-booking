package com.example.demo.repository;

import com.example.demo.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    void deleteByRoomDetailsId(Long id);
}
