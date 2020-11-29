package com.julieandco.bookcrossingMain.entity;

import java.util.UUID;

public class Book {
    private String title;
    private String author;
    private long year;
    private double rating;
    private Genre genre;
    private boolean available;
    private boolean needRepair;

    public Book() {
        title="";
        author="";
        year=0;
        rating=0;
        available=true;
        needRepair=false;
    }

    public Book(String title, String author, long year, double rating, Genre genre){
        this.title=title;
        this.author=author;
        this.year=year;
        this.rating=rating;
        this.genre=genre;
        this.available=true;
        this.needRepair=false;
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

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setNeedRepair(boolean needRepair) {
        this.needRepair = needRepair;
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

    public boolean getAvailability(){
        return available;
    }

    public boolean getRepair(){
        return needRepair;
    }


    public String getAuthor() {
        return author;
    }
    
    @Override
    public String toString(){
        String bookString = "Book: " + title;
        String authorString = "\r\n Author: " + author;
        String genreString = "\r\n Genre: " + genre;
        String yearString = "\r\n Year: " + year;
        String ratingString = "\r\n Rating: " + rating;

        return  bookString + authorString + genreString + yearString + ratingString;
    }
}
