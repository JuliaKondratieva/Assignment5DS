package com.julieandco.bookcrossing.book.rabbitmq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.book.entity.Book;
import com.julieandco.bookcrossing.book.entity.Genre;
import com.julieandco.bookcrossing.book.entity.dto.BookDTO;
import com.julieandco.bookcrossing.book.repo.BookRepo;
import com.julieandco.bookcrossing.book.service.BookService;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class BookReceiver {
    private final BookService bookService;
    //private final BookRepo bookRepo;
    @Autowired
    public BookReceiver(BookService bookService) {
        this.bookService = bookService;
    }
    private final static String QUEUE_NAME = "bookqueue";
    //@PostConstruct
    /*public void BookPost() throws Exception{
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
            System.out.println("CHECKKKK----\n");
            System.out.println("TITLE: "+title);
            System.out.println("AUTHOR: "+author);
            System.out.println("GENRE: "+genre.toString());
            System.out.println("YEAR: "+year);
            System.out.println("RATING: "+rating);
            Book newBook = new Book(title, author, year, rating, genre);
            System.out.println("CREATED NEW BOOK");
            if (bookService==null)
                System.out.println("FNULL BOOKSERVICE");
            else
                System.out.println("FNOTNULL");

            bookService.addBook(newBook);
            System.out.println("CALLED ADD BOOK");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }*/

    private static final String RPC_QUEUE_NAME = "rpc_queue_book";

    /*private static BookDTO findbook(BookDTO bookDTO) {
        bookService
        BookDTO foundbook=new BookDTO();
        return foundbook;
    }*/
    @PostConstruct
    public void RRPost() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queuePurge(QUEUE_NAME);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Object monitor = new Object();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";
                try {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    BookDTO bookDTO = gson.fromJson(message, BookDTO.class);
                    String title = bookDTO.getTitle();
                    System.out.println("GOT REQUEST FOR POSTING: "+ title);
                    Book toadd = new Book(bookDTO.getTitle(), bookDTO.getAuthor(), (int) bookDTO.getYear(), bookDTO.getRating(), bookDTO.getGenre());
                    System.out.println("------\nCHECKCHECK");
                    System.out.println("TITLE "+toadd.getTitle());
                    System.out.println("AUTHOR "+toadd.getAuthor());
                    System.out.println("YEAR "+toadd.getYear());
                    System.out.println("RATING "+toadd.getRating());
                    System.out.println("GENRE "+toadd.getGenre().toString());
                    System.out.println("ADDING BOOK");
                    if(bookService==null)
                        System.out.println("FNULL");
                    else
                        System.out.println("NOT FNULL");
                    bookService.addBook(toadd);
                    System.out.println("ADDED");
                    System.out.println(" [.] fib(" + message + ")");
                    response = "deliveredbook";
                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    System.out.println("FINALLY ITS FINALLY");
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    //bookService.addBook(toadd);
                    System.out.println("ADDED");
                    // RabbitMq consumer worker thread notifies the RPC server owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(QUEUE_NAME, false, deliverCallback, (consumerTag -> {
            }));
            // Wait and be prepared to consume the message from RPC client.
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //@PostConstruct
    public void RRFunct() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            channel.queuePurge(RPC_QUEUE_NAME);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Object monitor = new Object();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";

                try {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    BookDTO bookDTO = gson.fromJson(message, BookDTO.class);
                    String title = bookDTO.getTitle();
                    Book foundbook = bookService.findByTitle(title);
                    BookDTO tosend=new BookDTO(foundbook.getId(), foundbook.getTitle(), foundbook.getAuthor(), foundbook.getYear(), foundbook.getRating(), foundbook.getGenre());
                    Gson gson2 = new GsonBuilder().disableHtmlEscaping().create();
                    String bookJson = gson2.toJson(tosend);
                    System.out.println(" [.] fib(" + message + ")");
                    response = bookJson;
                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    // RabbitMq consumer worker thread notifies the RPC server owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));
            // Wait and be prepared to consume the message from RPC client.
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

