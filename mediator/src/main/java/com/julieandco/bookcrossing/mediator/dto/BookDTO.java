package com.julieandco.bookcrossing.mediator.dto;

import java.util.UUID;

public class BookDTO {
    private UUID id;
    private String title;
    private String author;
    private long year;
    private double rating;
    private GenreDTO genreDTO;

    public BookDTO(){}

    public BookDTO(String title, String author, long year, double rating, GenreDTO genre){
        this.title=title;
        this.author=author;
        this.rating=rating;
        this.year=year;
        this.genreDTO=genre;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setGenre(GenreDTO genre) {
        this.genreDTO = genre;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getTitle(){
        return title;
    }

    public GenreDTO getGenre(){
        return genreDTO;
    }

    public double getRating(){
        return rating;
    }

    public long getYear(){
        return year;
    }

    public String getAuthor() {
        return author;
    }
}
