package com.example.demo.service;

import com.example.demo.entities.Ticket;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;


    public ResponseEntity<?> getTickets() {
        ResponseEntity<?> response = null;

        try{
            List<Ticket> ticketList = ticketRepository.findAll();

            if (ticketList.isEmpty()) {
                response = new ResponseEntity<>("You have 0 tickets.", HttpStatus.NO_CONTENT);
            } else {
                response = new ResponseEntity<>(ticketList, HttpStatus.OK);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
