package com.julieandco.bookcrossing.mediator.controllers;

import com.julieandco.bookcrossing.mediator.dto.BookDTO;
import com.julieandco.bookcrossing.mediator.dto.CustomerDTO;
import com.julieandco.bookcrossing.mediator.dto.CustomersDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final String URL = "http://bookcrossingcust:8002";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final HttpEntity<Object> headersEntity = new HttpEntity<>(headers);

    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody CustomerDTO userDTO) {
        HttpEntity<CustomerDTO> saveUser = new HttpEntity<>(userDTO);
        ResponseEntity<Void> response1 = restTemplate
                .exchange(URL + "/api/customers/save", HttpMethod.POST,
                        saveUser, Void.class);

        return ResponseEntity.ok().build();

    }

    @GetMapping
    public @ResponseBody
    CustomersDTO getAllCustomers() {
        ResponseEntity<CustomersDTO> response2 = restTemplate
                .exchange(URL + "/customers", HttpMethod.GET, headersEntity, CustomersDTO.class);
        List<CustomerDTO> usersFromDB = Objects.requireNonNull(response2.getBody()).getUsers();
        CustomersDTO customersDTO = new CustomersDTO();
        customersDTO.setUsers(usersFromDB);
        return customersDTO;
    }

    private String exchangec = "exchange";
    //@Value("${rabbitmq.routing-key}")
    private String routingKeyc = "mediator.to.customer";
    //@Value("${rabbitmq.queue}")
    private String queuec = "customerqueue";

    @PostMapping("/save/rabbitmq")
    public void sendUserToSave(@RequestBody CustomerDTO customerDTO) {
        System.out.println("CUSTOMER CONTROLLER");
        rabbitTemplate.convertAndSend(exchangec, routingKeyc, customerDTO);
        System.out.println("Sent customer");
    }
}
