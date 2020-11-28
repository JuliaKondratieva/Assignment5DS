package com.julieandco.bookcrossing.mediator.dto;

public class DeliveryDTO {
    private BookDTO book;
    private BoxDTO box;

    public DeliveryDTO(BoxDTO box, BookDTO book) {
        this.book = book;
        this.box= box;
    }

    public void setBox(BoxDTO box) {
        this.box = box;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public BookDTO getBook() {
        return book;
    }

    public BoxDTO getBox() {
        return box;
    }
}
