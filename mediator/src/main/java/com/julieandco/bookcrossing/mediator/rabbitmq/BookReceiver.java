package com.julieandco.bookcrossing.mediator.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.mediator.dto.BookDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeoutException;
@Component
public class BookReceiver {
    private final static String QUEUE_NAME = "bookqueue";
    private BookSender bookSender;
    @PostConstruct
    public void BookReceive() throws Exception {
        //BookSender bookSender=new BookSender();
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
            try {
                bookSender.BookPost(bookDTO);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
