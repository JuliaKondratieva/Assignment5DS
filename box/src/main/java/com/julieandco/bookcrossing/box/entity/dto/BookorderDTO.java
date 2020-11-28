package com.julieandco.bookcrossing.box.entity.dto;

import java.util.List;
import java.util.UUID;

public class BookorderDTO {
    private List<UUID> bookorders;

    public BookorderDTO() {
    }

    public BookorderDTO(List<UUID> bookorders) {
        this.bookorders = bookorders;
    }

    public List<UUID> getBookorders() {
        return bookorders;
    }

    public void setBookorders(List<UUID> bookorders) {
        this.bookorders = bookorders;
    }
}
