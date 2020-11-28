package com.julieandco.bookcrossing.customer.service;

import com.julieandco.bookcrossing.customer.entity.Customer;
import com.julieandco.bookcrossing.customer.entity.dto.CustomerDTO;
import com.julieandco.bookcrossing.customer.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CustomerService {
    private final CustomerRepo customerRepository;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepository = customerRepo;
    }

    @Transactional
    public List<Customer> getAllUsers(){
        return customerRepository.findAll();
    }

    @Transactional
    public void addUser(Customer user){
        if(customerRepository.findByUsername(user.getUsername()) == null){
            customerRepository.save(user);
        }
    }

    @Transactional
    public Customer findByUsername(String username){
        return customerRepository.findByUsername(username);
    }
}
