package com.julieandco.bookcrossing.book.rabbitmq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.book.entity.Book;
import com.julieandco.bookcrossing.book.entity.Genre;
import com.julieandco.bookcrossing.book.entity.dto.BookDTO;
import com.julieandco.bookcrossing.book.service.BookService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
public class BookReceiver {

    @Autowired
    private final BookService bookService;
    public BookReceiver(BookService bookService) {
        this.bookService = bookService;
    }
    private final static String QUEUE_NAME = "bookqueue";
    @PostConstruct
    public void BookPost() throws Exception{
        /*Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        BookDTO bookDTO = gson.fromJson(message, BookDTO.class);
        String title = bookDTO.getTitle();
        String author=bookDTO.getAuthor();
        Genre genre = bookDTO.getGenre();
        int year = (int) bookDTO.getYear();
        double rating=bookDTO.getRating();
        Book newBook = new Book(title, author, year, rating, genre);
        bookService.addBook(newBook);*/
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            BookDTO bookDTO = gson.fromJson(message, BookDTO.class);
            String title = bookDTO.getTitle();
            String author=bookDTO.getAuthor();
            Genre genre = bookDTO.getGenre();
            int year = (int) bookDTO.getYear();
            double rating=bookDTO.getRating();
            Book newBook = new Book(title, author, year, rating, genre);
            bookService.addBook(newBook);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}

