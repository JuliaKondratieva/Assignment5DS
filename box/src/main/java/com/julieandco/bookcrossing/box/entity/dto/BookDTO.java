package com.julieandco.bookcrossing.box.entity.dto;

import java.util.UUID;

public class BookDTO {
    private UUID id;
    private String title;
    private String author;
    private int year;
    private double rating;
    private GenreDTO genreDTO;

    public BookDTO(){}
    public BookDTO(UUID id, String title, String author, int year, double rating, GenreDTO genre){
        this.id=id;
        this.title=title;
        this.author=author;
        this.year=year;
        this.rating=rating;
        this.genreDTO=genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setYear(int year) {
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

    public int getYear(){
        return year;
    }

    public String getAuthor() {
        return author;
    }
}
