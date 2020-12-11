package com.julieandco.bookcrossing.customer.rabbitmq;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.customer.entity.Customer;
import com.julieandco.bookcrossing.customer.entity.dto.CustomerDTO;
import com.julieandco.bookcrossing.customer.service.CustomerService;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class CustomerReceiver {


    private final CustomerService customerService;
    @Autowired
    public CustomerReceiver(CustomerService customerService) {
        this.customerService = customerService;
    }

    private final static String QUEUE_NAME = "customerqueue";

    @PostConstruct
    public void CustomerPost() throws Exception {
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
            CustomerDTO customerDTO = gson.fromJson(message, CustomerDTO.class);
            String username = customerDTO.getUsername();
            System.out.println("RECEIVER CHECK username="+username);
            Customer newCustomer = new Customer(username);
            customerService.addUser(newCustomer);
            System.out.println("ADDED USER");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    private static final String RPC_QUEUE_NAME = "rpc_queue_customer";

    /*private static BookDTO findbook(BookDTO bookDTO) {
        bookService
        BookDTO foundbook=new BookDTO();
        return foundbook;
    }*/
    @PostConstruct
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
                    CustomerDTO customerDTO = gson.fromJson(message, CustomerDTO.class);
                    String username = customerDTO.getUsername();
                    Customer founduser = customerService.findByUsername(username);
                    CustomerDTO tosend=new CustomerDTO(founduser.getIdNumber(), founduser.getUsername());
                    Gson gson2 = new GsonBuilder().disableHtmlEscaping().create();
                    String userJson = gson2.toJson(tosend);
                    System.out.println(" [.] fib(" + message + ")");
                    response = userJson;
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