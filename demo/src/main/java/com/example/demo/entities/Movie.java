package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;
    private String description;
    private String duration;
    private Integer year;

    @NotNull
    private String ageRestriction;
    private String director;
    private String imgUrl;
    private String trailerUrl;

    /*@ManyToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name="room_id", nullable=false)*/
    @JsonIgnoreProperties("movies")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_date_id", referencedColumnName = "id")
    private MovieDate movieDate;

    @JsonIgnoreProperties("movie")
    @OneToMany(mappedBy = "movie")
    private List<MoviePlayingHours> startingHours;

    public List<MoviePlayingHours> getStartingHours() {
        return startingHours;
    }

    public void setStartingHours(List<MoviePlayingHours> startingHours) {
        this.startingHours = startingHours;
    }

    public Movie(){
        this.startingHours = new ArrayList<>();
    }

    public Movie(Long id, String title, String description, String duration, Integer year, String ageRestriction, String director, String imgUrl, String trailerUrl, MovieDate movieDate) {
        this.id = id;


        this.title = title;
        this.description = description;

        this.duration = duration;
        this.year = year;
        this.ageRestriction = ageRestriction;
        this.director = director;
        this.imgUrl = imgUrl;
        this.trailerUrl = trailerUrl;
        this.movieDate = movieDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public MovieDate getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(MovieDate movieDate) {
        this.movieDate = movieDate;
    }
}
