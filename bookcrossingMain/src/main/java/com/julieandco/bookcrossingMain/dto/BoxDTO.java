package com.julieandco.bookcrossingMain.dto;

import com.julieandco.bookcrossingMain.entity.Book;

import java.util.List;
import java.util.UUID;

public class BoxDTO {
    private List<Book> books;
    private String address;

    public BoxDTO() {
    }

    public BoxDTO(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
