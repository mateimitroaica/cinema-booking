package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_hours")
public class MoviePlayingHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieFormat;

    @Temporal(TemporalType.TIME)
    private Time startingHour;

    private Integer ticketPrice;

    private Integer roomNumber;

    @NotNull
    private Integer roomCapacity;

    public Integer getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(Integer roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    @JsonIgnoreProperties("startingHours")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @JsonIgnoreProperties("roomDetails")
    @OneToMany(mappedBy = "roomDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MoviePlayingHours(){
        this.seats = new ArrayList<>();
    }

    public MoviePlayingHours(Long id, String movieFormat, Time startingHour, Integer ticketPrice, Integer roomNumber, Integer roomCapacity) {
        this.id = id;
        this.movieFormat = movieFormat;
        this.startingHour = startingHour;
        this.ticketPrice = ticketPrice;
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieFormat() {
        return movieFormat;
    }

    public void setMovieFormat(String movieFormat) {
        this.movieFormat = movieFormat;
    }

    public Time getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(Time startingHour) {
        this.startingHour = startingHour;
    }

    public Integer getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Integer ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
}
