package com.julieandco.bookcrossing.mediator.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.mediator.dto.BookDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class BookSender {
    private final static String QUEUE_NAME = "bookqueuegateway";

    public void BookPost(BookDTO bookDTO) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq-service");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String bookJson = gson.toJson(bookDTO);
            channel.basicPublish("", QUEUE_NAME, null, bookJson.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + bookJson + "'");
        }
    }
}
