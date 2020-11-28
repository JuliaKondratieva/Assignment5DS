package com.julieandco.bookcrossing.bookorder.entity.dto;

import java.util.ArrayList;
import java.util.List;

public class BookorderDTO {
    private List<SubmitBookorderDTO> bookorders;

    public BookorderDTO() {
        bookorders=new ArrayList<>();
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
