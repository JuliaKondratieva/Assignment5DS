package com.julieandco.bookcrossing.mediator.dto;

import java.time.LocalDateTime;

public class SubmitBookorderDTO {
    private CustomerDTO user;
    private BookDTO book;
    private LocalDateTime fromDate;
    private LocalDateTime dueDate;
    private boolean deliveryState;
    private boolean submitted;

    public SubmitBookorderDTO(){

    }

    public SubmitBookorderDTO(BookDTO book, CustomerDTO user){
        this.book=book;
        this.user=user;
    }


    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setDeliveryState(boolean deliveryState) {
        this.deliveryState = deliveryState;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public boolean isDeliveryState() {
        return deliveryState;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public CustomerDTO getUser() {
        return user;
    }

    public void setUser(CustomerDTO user) {
        this.user = user;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }
}
