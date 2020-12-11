package com.julieandco.bookcrossingMain.dto;

import com.julieandco.bookcrossingMain.entity.Genre;

public class BookDTO {
    private String title;
    private String author;
    private long year;
    private double rating;
    private Genre genre;
    private boolean available;
    private boolean needRepair;

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
}
