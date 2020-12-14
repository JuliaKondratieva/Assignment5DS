package com.julieandco.bookcrossing.book.rabbitmq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.book.entity.Book;
import com.julieandco.bookcrossing.book.entity.Genre;
import com.julieandco.bookcrossing.book.entity.dto.BookDTO;
import com.julieandco.bookcrossing.book.entity.dto.BookorderDTO;
import com.julieandco.bookcrossing.book.repo.BookRepo;
import com.julieandco.bookcrossing.book.service.BookService;
import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class BookReceiver {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final BookService bookService;
    @Autowired
    public BookReceiver(BookService bookService) {
        this.bookService = bookService;
    }
    private final static String QUEUE_NAME = "bookqueue";

    @RabbitListener(queues = QUEUE_NAME)
    public void consume(BookDTO bookDTO) {
        Book book=new Book(bookDTO.getTitle(), bookDTO.getAuthor(), (int)bookDTO.getYear(), bookDTO.getRating(), bookDTO.getGenre());
        System.out.println("CONSUMER TRIGGERED");
        bookService.addBook(book);
    }
}

