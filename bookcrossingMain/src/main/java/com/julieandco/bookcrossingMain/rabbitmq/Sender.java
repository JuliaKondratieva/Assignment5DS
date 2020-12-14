package com.julieandco.bookcrossingMain.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julieandco.bookcrossingMain.dto.BookDTO;
import com.julieandco.bookcrossingMain.dto.BoxDTO;
import com.julieandco.bookcrossingMain.dto.SubmitOrderDTO;
import com.julieandco.bookcrossingMain.dto.UserDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Sender {
    private static final String URL = "http://localhost:8081";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final HttpEntity<Object> headersEntity = new HttpEntity<>(headers);
    public void requestBookRabbitmq(BookDTO bookDTO){//Object entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<BookDTO> saveBook = new HttpEntity<>(bookDTO);
        ResponseEntity<Void> response0 = restTemplate
                .exchange(URL + "/api/books/save/rabbitmq", HttpMethod.POST,
                        saveBook, Void.class);
    }
    public void requestUserRabbitmq(UserDTO userDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<UserDTO> saveUser = new HttpEntity<>(userDTO);
        ResponseEntity<Void> response0 = restTemplate
                .exchange(URL + "/api/customers/save/rabbitmq", HttpMethod.POST,
                        saveUser, Void.class);
    }

    public void requestOrderRabbitmq(SubmitOrderDTO submitOrderDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<SubmitOrderDTO> saveOrder = new HttpEntity<>(submitOrderDTO);
        ResponseEntity<Void> response0 = restTemplate
                .exchange(URL + "/api/bookorders/save/rabbitmq", HttpMethod.POST,
                        saveOrder, Void.class);
    }

    public void requestBoxRabbitmq(BoxDTO boxDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<BoxDTO> saveBox = new HttpEntity<>(boxDTO);
        ResponseEntity<Void> response0 = restTemplate
                .exchange(URL + "/api/boxes/save/rabbitmq", HttpMethod.POST,
                        saveBox, Void.class);
    }
}
