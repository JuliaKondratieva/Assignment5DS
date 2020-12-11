package com.julieandco.bookcrossingMain.dto;

import com.julieandco.bookcrossingMain.entity.Book;
import com.julieandco.bookcrossingMain.entity.Box;

public class DeliveryDTO {

    private BoxDTO box;
    private BookDTO book;

    public DeliveryDTO(){

    }

    public DeliveryDTO(BookDTO book, BoxDTO box) {
        this.box= box;
        this.book = book;
    }

    public void setBox(BoxDTO box) {
        this.box = box;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public BoxDTO getBox() {
        return box;
    }
}
