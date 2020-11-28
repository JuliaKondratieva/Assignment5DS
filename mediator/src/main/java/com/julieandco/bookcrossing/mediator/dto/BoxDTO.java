package com.julieandco.bookcrossing.mediator.dto;

import java.util.List;
import java.util.UUID;

public class BoxDTO {
    private UUID id;
    private List<UUID> books;
    private String address;

    public BoxDTO() {

    }

    public List<UUID> getBooks() {
        return books;
    }

    public UUID getId() {
        return id;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public String getAddress() {
        return address;
    }

    public void setBooks(List<UUID> books) {
        this.books = books;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
