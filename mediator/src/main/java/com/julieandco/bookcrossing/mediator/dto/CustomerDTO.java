package com.julieandco.bookcrossing.mediator.dto;

import java.util.UUID;

public class CustomerDTO {
    private UUID id;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
