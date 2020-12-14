package com.julieandco.bookcrossing.bookorder.rabbitmq;

import com.julieandco.bookcrossing.bookorder.entity.Bookorder;
import com.julieandco.bookcrossing.bookorder.entity.dto.*;
import com.julieandco.bookcrossing.bookorder.service.BookorderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Component
public class OrderReceiver {
    private static final String URL = "http://localhost:8001";
    private static final String URLC = "http://localhost:8002";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final HttpEntity<Object> headersEntity = new HttpEntity<>(headers);
    private final BookorderService bookorderService;
    @Autowired
    public OrderReceiver(BookorderService bookorderService) {
        this.bookorderService = bookorderService;
    }
    private final static String QUEUE_NAME = "orderqueue";

    @RabbitListener(queues = QUEUE_NAME)
    public void consume(SubmitBookorderDTO orderDTO) {
        System.out.println("CONSUMER TRIGGERED");
        CustomerDTO user = orderDTO.getUser();
        System.out.println("RECEIVED FROM JSON USER: "+user.getUsername());

        BookDTO book = orderDTO.getBook();
        System.out.println("RECEIVED FROM JSON BOOK: "+book.getTitle());
        BookDTO bookDTO=new BookDTO();
        CustomerDTO customerDTO = new CustomerDTO();
        boolean submitted = false;
        boolean custFound = false;
        System.out.println("SENDING CUSTOMER REQUEST");

        ResponseEntity<CustomersDTO> response5 = restTemplate
                .exchange(URLC + "/api/customers", HttpMethod.GET, headersEntity, CustomersDTO.class);

        for (CustomerDTO c : Objects.requireNonNull(response5.getBody()).getUsers()) {
            System.out.println("RECEIVED REQUEST: c: "+c.getUsername());
            if(c.getUsername().equals(user.getUsername())) {
                System.out.println("C==USER");
                System.out.println("сID: "+ c.getId().toString());

                customerDTO=c;
                custFound=true;
                break;
            }
        }
        System.out.println("SENDING BOOK REQUEST");
        ResponseEntity<BooksDTO> response6 = restTemplate
                .exchange(URL + "/api/books", HttpMethod.GET, headersEntity, BooksDTO.class);

        for (BookDTO b : Objects.requireNonNull(response6.getBody()).getBooks()) {
            System.out.println("RECEIVED REQUEST: b: "+b.getTitle());
            if(b.getTitle().equals(book.getTitle())) {
                System.out.println("B==BOOK");
                System.out.println("bID: "+ b.getId().toString());
                bookDTO=b;
                submitted=true;
                break;
            }
        }
        if(!submitted)
            System.out.println("Book doesnt exist!");
        System.out.println("bookID "+bookDTO.getId().toString());
        if(submitted&&custFound)
            bookorderService.addOrder(bookDTO, customerDTO);
        //bookorderService.addOrder(orderDTO.getBook(), orderDTO.getUser());
    }
}
