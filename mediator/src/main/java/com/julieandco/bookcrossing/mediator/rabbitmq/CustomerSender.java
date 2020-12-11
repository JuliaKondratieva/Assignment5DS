package com.julieandco.bookcrossing.mediator.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.mediator.dto.BookDTO;
import com.julieandco.bookcrossing.mediator.dto.CustomerDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class CustomerSender {
    private final static String QUEUE_NAME = "customerqueuegateway";

    public void UserPost(CustomerDTO customerDTO) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq-service");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String customerJson = gson.toJson(customerDTO);
            channel.basicPublish("", QUEUE_NAME, null, customerJson.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + customerJson + "'");
        }
    }
}
