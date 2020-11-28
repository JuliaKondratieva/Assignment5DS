package com.julieandco.bookcrossing.mediator.dto;

import java.util.List;

public class BookorderDTO {
    private List<SubmitBookorderDTO> bookorders;

    public BookorderDTO() {
    }

    public BookorderDTO(List<SubmitBookorderDTO> bookorders) {
        this.bookorders = bookorders;
    }

    public List<SubmitBookorderDTO> getBookorders() {
        return bookorders;
    }

    public void setBookorders(List<SubmitBookorderDTO> bookorders) {
        this.bookorders = bookorders;
    }
}
