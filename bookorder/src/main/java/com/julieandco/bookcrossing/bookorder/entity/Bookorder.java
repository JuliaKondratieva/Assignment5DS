package com.julieandco.bookcrossing.bookorder.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="bookorder")
public class Bookorder {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private UUID bookId;
    @Column
    private UUID customerId;
    @Column
    private LocalDateTime fromDate;
    @Column
    private LocalDateTime dueDate;
    @Column
    private boolean deliveryState;
    @Column
    private boolean submitted;

    public Bookorder()
    {
        this.fromDate=LocalDateTime.now();
        this.dueDate=fromDate.plusWeeks(3);
        this.deliveryState=false;
        this.submitted=true;
    }

    public Bookorder(UUID book, UUID user, LocalDateTime fr, LocalDateTime due, boolean del, boolean sub){
        this.bookId=book;
        this.customerId=user;
        this.fromDate=fr;
        this.dueDate=due;
        this.deliveryState=del;
        this.submitted=sub;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBookId(){
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
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

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public UUID getCustomerId(){
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void isDelivered(){
        this.deliveryState=true;
    }

    public boolean getDeliveryState(){
        return deliveryState;
    }

    public LocalDateTime getDueDate(){
        return dueDate;
    }

    public String toString(){
        return "Book: " + bookId.toString() + "\r\n User: " + customerId.toString() + "\r\n Order date: " + fromDate.toString() + "\r\n due Date: " + dueDate.toString();
    }
}
