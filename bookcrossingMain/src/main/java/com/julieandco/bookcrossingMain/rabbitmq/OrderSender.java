package com.julieandco.bookcrossingMain.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossingMain.dto.SubmitOrderDTO;
import com.julieandco.bookcrossingMain.dto.UserDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class OrderSender {
    private final static String QUEUE_NAME = "orderqueue";

    public void OrderPost(SubmitOrderDTO submitOrderDTO) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String orderJson = gson.toJson(submitOrderDTO);
            channel.basicPublish("", QUEUE_NAME, null, orderJson.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + orderJson + "'");
        }
    }
}
