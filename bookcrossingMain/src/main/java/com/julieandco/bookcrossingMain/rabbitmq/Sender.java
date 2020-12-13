package com.julieandco.bookcrossingMain.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julieandco.bookcrossingMain.dto.BookDTO;
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
    public void requestRabbitmq(String path, BookDTO bookDTO){//Object entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<BookDTO> saveBook = new HttpEntity<>(bookDTO);
        ResponseEntity<Void> response0 = restTemplate
                .exchange(URL + "/api/books/save/rabbitmq", HttpMethod.POST,
                        saveBook, Void.class);
       /* try {
            String entityJson = objectMapper.writeValueAsString(entity);
            HttpEntity<String> entityJsonHttp = new HttpEntity<>(entityJson, headers);
            ResponseEntity<Void> response = restTemplate.postForEntity(URL +
                    path + "/rabbitmq", entityJsonHttp, Void.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/
    }
}
