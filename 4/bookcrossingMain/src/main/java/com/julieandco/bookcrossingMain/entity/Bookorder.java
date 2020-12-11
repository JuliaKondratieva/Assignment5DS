package com.julieandco.bookcrossingMain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.UUID;

public class Bookorder {
    private UUID id;
    private Book book;
    private User customer;
    private LocalDate fromDate;
    private LocalDate dueDate;
    private boolean deliveryState;
    private boolean submitted;

    public Bookorder()
    {
        book=new Book();
        customer=new User();
        deliveryState=false;
        submitted=true;
    }

    public Bookorder(Book book, User user){
        this.book=book;
        this.customer=user;
        this.fromDate=LocalDate.now();
        this.dueDate=fromDate.plusWeeks(3);
        this.deliveryState=false;
        this.submitted=true;
    }

    public Book getBook(){
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public UUID getId() {
        return id;
    }

    public boolean getSubmitted() {
        return submitted;
    }

    public void setDeliveryState(boolean deliveryState) {
        this.deliveryState = deliveryState;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public User getUser(){
        return customer;
    }

    public void setUser(User user) {
        this.customer = user;
    }

    public void isDelivered(){
        this.deliveryState=true;
    }

    public boolean getDeliveryState(){
        return deliveryState;
    }

    public LocalDate getDueDate(){
        return dueDate;
    }

    public String toString(){
        return "Book: " + book.getTitle() + "\r\n User: " + customer.getUsername() + customer.getIdNumber().toString() + "\r\n Order date: " + fromDate.toString() + "\r\n due Date: " + dueDate.toString();
    }

}
