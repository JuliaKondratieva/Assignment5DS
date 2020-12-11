package com.julieandco.bookcrossingMain.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossingMain.dto.BookDTO;
import com.julieandco.bookcrossingMain.dto.UserDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class CustomerSender {
    private final static String QUEUE_NAME = "customerqueue";

    public void UserPost(UserDTO userDTO) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String userJson = gson.toJson(userDTO);
            channel.basicPublish("", QUEUE_NAME, null, userJson.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + userJson + "'");
        }
    }
}
