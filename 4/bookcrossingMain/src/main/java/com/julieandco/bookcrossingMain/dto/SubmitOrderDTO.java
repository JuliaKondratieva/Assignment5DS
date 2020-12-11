package com.julieandco.bookcrossingMain.dto;

import com.julieandco.bookcrossingMain.entity.Book;
import com.julieandco.bookcrossingMain.entity.User;

public class SubmitOrderDTO {
    private UserDTO user;
    private BookDTO book;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }
}