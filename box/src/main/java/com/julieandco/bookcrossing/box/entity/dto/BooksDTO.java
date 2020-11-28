package com.julieandco.bookcrossing.box.entity.dto;

import java.util.List;

public class BooksDTO {
    private List<BookDTO> books;

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
