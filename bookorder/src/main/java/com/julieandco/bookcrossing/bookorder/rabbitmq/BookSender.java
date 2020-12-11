package com.julieandco.bookcrossing.bookorder.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.bookorder.entity.dto.BookDTO;
import com.julieandco.bookcrossing.bookorder.entity.dto.CustomerDTO;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class BookSender implements AutoCloseable{
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue_book";

    public BookSender() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public String RRFunct(BookDTO bookDTO) {
        try (BookSender getBookRpc = new BookSender()) {
            System.out.println(" [x] Requesting customer(" + bookDTO.getTitle() + ")");
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String booJson = gson.toJson(bookDTO);
            String response = getBookRpc.call(booJson);
            System.out.println(" [.] Got '" + response + "'");
            return response;

        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
            return ""; //////////returns????
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
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
