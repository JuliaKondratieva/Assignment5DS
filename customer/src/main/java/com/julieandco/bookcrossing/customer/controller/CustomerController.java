package com.julieandco.bookcrossing.customer.controller;

import com.julieandco.bookcrossing.customer.entity.Customer;
import com.julieandco.bookcrossing.customer.entity.dto.CustomerDTO;
import com.julieandco.bookcrossing.customer.entity.dto.CustomersDTO;
import com.julieandco.bookcrossing.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody CustomerDTO userDTO){
        String customerUsername = userDTO.getUsername();
        Customer customer=new Customer();
        customer.setUsername(customerUsername);
        customerService.addUser(customer);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public @ResponseBody
    CustomersDTO getAllCustomers(){
        CustomersDTO usersDTO = new CustomersDTO();
        List<Customer> customers = customerService.getAllUsers();
        for(Customer c:customers){
            CustomerDTO customerDTO = new CustomerDTO(c.getIdNumber(), c.getUsername());
            usersDTO.getUsers().add(customerDTO);
        }
        return usersDTO;
    }
}
