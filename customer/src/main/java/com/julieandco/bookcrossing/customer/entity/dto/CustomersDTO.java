package com.julieandco.bookcrossing.customer.entity.dto;

import com.julieandco.bookcrossing.customer.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersDTO {
    private List<CustomerDTO> users;

    public CustomersDTO(){
        users=new ArrayList<>();
    }

    public List<CustomerDTO> getUsers() {
        return users;
    }

    public void setUsers(List<CustomerDTO> users) {
        this.users = users;
    }
}
