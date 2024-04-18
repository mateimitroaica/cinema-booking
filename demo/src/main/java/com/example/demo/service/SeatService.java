package com.example.demo.service;

import com.example.demo.entities.Seat;
import com.example.demo.entities.Ticket;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public ResponseEntity<?> getSeatById(Long id) {
        ResponseEntity<?> response = null;

        Optional<Seat> optionalSeat = seatRepository.findById(id);

        if (optionalSeat.isPresent()) {
            response = new ResponseEntity<>(optionalSeat, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    public ResponseEntity<?> buyTicket(Long id) {
        ResponseEntity<?> response = null;

        try{
            Optional<Seat> optionalSeat = seatRepository.findById(id);

            if (optionalSeat.isPresent()) {
                Seat seat = optionalSeat.get();

                if (seat.isSeatAvailable()) {

                    seat.setSeatAvailable(false);
                    Ticket ticket = new Ticket();

                    ticket.setSeat(seat);
                    seat.setTicket(ticket);

                    seatRepository.saveAndFlush(seat);
                    response = new ResponseEntity<>("Thank you for your purchase!", HttpStatus.CREATED);

                } else {
                    response = new ResponseEntity<>("Seat already taken.", HttpStatus.BAD_REQUEST);
                }

            } else {
                response = new ResponseEntity<>("Wrong id.", HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
