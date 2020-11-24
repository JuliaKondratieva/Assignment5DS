package com.julieandco.bookcrossing.book.entity.dto;

import com.julieandco.bookcrossing.book.entity.Genre;

import java.util.UUID;

public class BookDTO {
    private UUID id;
    private String title;
    private String author;
    private long year;
    private double rating;
    private Genre genre;

    public BookDTO(){}

    public BookDTO(UUID id, String title, String author, long year, double rating, Genre genre){
        this.id = id;
        this.title=title;
        this.author=author;
        this.rating=rating;
        this.year=year;
        this.genre=genre;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getTitle(){
        return title;
    }

    public Genre getGenre(){
        return genre;
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
