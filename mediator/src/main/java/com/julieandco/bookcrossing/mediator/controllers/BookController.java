package com.julieandco.bookcrossing.mediator.controllers;

import com.julieandco.bookcrossing.mediator.dto.BookDTO;
import com.julieandco.bookcrossing.mediator.dto.BooksDTO;
import com.julieandco.bookcrossing.mediator.dto.CustomerDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final String URL = "http://localhost:8001";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final HttpEntity<Object> headersEntity = new HttpEntity<>(headers);

    @GetMapping
    public @ResponseBody
    BooksDTO getAllProducts(){
        ResponseEntity<BooksDTO> response2 = restTemplate
                .exchange(URL + "/books", HttpMethod.GET, headersEntity, BooksDTO.class);
        List<BookDTO> booksFromDB = Objects.requireNonNull(response2.getBody()).getBooks();
        BooksDTO booksDTO = new BooksDTO();
        booksDTO.setBooks(booksFromDB);
        return booksDTO;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveBook(@RequestBody BookDTO bookDTO){
        HttpEntity<BookDTO> saveBook = new HttpEntity<>(bookDTO);
        ResponseEntity<Void> response1 = restTemplate
                .exchange(URL + "/api/books/save", HttpMethod.POST,
                        saveBook, Void.class);

        return ResponseEntity.ok().build();
    }
}
