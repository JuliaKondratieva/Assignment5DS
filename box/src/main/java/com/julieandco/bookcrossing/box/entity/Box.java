package com.julieandco.bookcrossing.box.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="box")
public class Box {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private UUID books;
    @Column
    private String address;

    public Box() {

    }

    public Box(UUID id, UUID books, String address){
        this.id = id;
        this.books=books;
        this.address=address;
    }

    public UUID getBooks() {
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

    public void setBooks(UUID books) {
        this.books = books;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", books=" + books.toString() +
                ", address='" + address + '\'' +
                '}';
    }
}
