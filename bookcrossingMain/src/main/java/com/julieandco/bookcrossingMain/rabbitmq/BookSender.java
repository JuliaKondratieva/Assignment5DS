package com.julieandco.bookcrossingMain.rabbitmq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossingMain.Config;
import com.julieandco.bookcrossingMain.dto.BookDTO;
import com.julieandco.bookcrossingMain.entity.Genre;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
@Component
public class BookSender {//implements AutoCloseable {
    private final RabbitTemplate rabbitTemplate;

    public BookSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
@PostConstruct
    public void send() throws Exception {
        BookDTO b1=new BookDTO();
        b1.setTitle("The Wayward Pines RAB");
        b1.setAuthor("Blake Crouch RAB");
        b1.setGenre(Genre.Thriller);
        b1.setRating(8);
        b1.setYear(2012);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String booJson = gson.toJson(b1);
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(Config.exchange, "main.to.book", booJson);
        System.out.println("SENT BOOK");
    }
}

   /* private Connection connection;
    private Channel channel;
    private String requestQueueName = "bookqueue";

    public BookSender() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void RRFunct(BookDTO bookDTO) {
        try (BookSender getBookRpc = new BookSender()) {
            System.out.println(" [x] Requesting book(" + bookDTO.getTitle() + ")");
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String booJson = gson.toJson(bookDTO);
            String response = getBookRpc.call(booJson);
            System.out.println(" [.] Got '" + response + "'");
            //return response;

        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
           // return ""; //////////returns????
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        BasicProperties props = new BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes(StandardCharsets.UTF_8));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }
}
   // private final static String QUEUE_NAME = "bookqueue";
/*
    public void BookPost(BookDTO bookDTO) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String bookJson = gson.toJson(bookDTO);
            channel.basicPublish("", QUEUE_NAME, null, bookJson.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + bookJson + "'");
        }
    }*/


