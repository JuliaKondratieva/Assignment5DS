package com.julieandco.bookcrossingMain.entity;

import java.util.ArrayList;
import java.util.List;


public class Box {
    private List<Book> books;
    private String address;

    public Box() {
        books=new ArrayList<>();
        address="";
    }

    public List<Book> getBooks() {
        return books;
    }


    public void setAddress(String address){
        this.address=address;
    }

    public String getAddress() {
        return address;
    }
}
