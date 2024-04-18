package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isSeatAvailable;

    @JsonIgnoreProperties("seats")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private MoviePlayingHours roomDetails;

    @JsonIgnoreProperties("seat")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public MoviePlayingHours getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(MoviePlayingHours roomDetails) {
        this.roomDetails = roomDetails;
    }

    public Seat(){
        this.isSeatAvailable = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSeatAvailable() {
        return isSeatAvailable;
    }

    public void setSeatAvailable(boolean seatAvailable) {
        isSeatAvailable = seatAvailable;
    }
}
