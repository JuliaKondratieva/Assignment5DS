package com.julieandco.bookcrossing.customer.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue
    private UUID idNumber;
    @Column
    private String username;

    public Customer() {
        username = "";
    }

    public Customer(String username) {
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
