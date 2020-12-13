package com.julieandco.bookcrossing.customer.rabbitmq;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.customer.entity.Customer;
import com.julieandco.bookcrossing.customer.entity.dto.CustomerDTO;
import com.julieandco.bookcrossing.customer.service.CustomerService;
import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class CustomerReceiver {
    private final CustomerService customerService;
    @Autowired
    public CustomerReceiver(CustomerService customerService) {
        this.customerService = customerService;
    }
    private final static String QUEUE_NAME = "customerqueue";

    @RabbitListener(queues = QUEUE_NAME)
    public void consume(CustomerDTO customerDTO) {
        Customer customer=new Customer(customerDTO.getUsername());
        System.out.println("CONSUMER TRIGGERED");
        customerService.addUser(customer);
    }
}