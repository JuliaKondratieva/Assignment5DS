package com.julieandco.bookcrossingMain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.UUID;

public class User {
    private UUID idNumber;
    private String username;


    public User() {
        username = "";
    }

    public User(String username) {
        this.username = username;
    }

    public UUID getIdNumber() {
        return idNumber;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

}
