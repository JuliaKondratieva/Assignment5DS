package com.julieandco.bookcrossing.book.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private long year;
    @Column
    private double rating;
    @Column
    @Enumerated(EnumType.STRING)
    private Genre genre;

    public Book() {

    }

    public Book(String title, String author, long year, double rating, Genre genre){
        this.title=title;
        this.author=author;
        this.year=year;
        this.rating=rating;
        this.genre=genre;
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

    public void setId(UUID id) {
        this.id = id;
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

    public UUID getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String toString(){
        String bookString = "Book: " + title;
        String authorString = "\r\n Author: " + author;
        String genreString = "\r\n Genre: " + genre;
        String yearString = "\r\n Year: " + year;
        String ratingString = "\r\n Rating: " + rating;

        return  bookString + authorString + genreString + yearString + ratingString;
    }

}
