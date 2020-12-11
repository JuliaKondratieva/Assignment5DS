package com.julieandco.bookcrossing.bookorder.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.bookorder.entity.dto.*;
import com.julieandco.bookcrossing.bookorder.service.BookorderService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Component
public class OrderReceiver {

    @Autowired
    private final BookorderService bookorderService;
    public OrderReceiver(BookorderService bookorderService) {
        this.bookorderService = bookorderService;
    }
    private final static String QUEUE_NAME = "orderqueue";
    private CustomerSender customerSender;

    @PostConstruct
    public void OrderPost() throws Exception{
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
            SubmitBookorderDTO orderDTO = gson.fromJson(message, SubmitBookorderDTO.class);
            /////////////////////////////////////
            Gson gson2 = new GsonBuilder().disableHtmlEscaping().create();
            CustomerDTO user = orderDTO.getUser();
            System.out.println("RECEIVED FROM JSON USER: "+user.getUsername());

            BookDTO book = orderDTO.getBook();
            System.out.println("RECEIVED FROM JSON BOOK: "+book.getTitle());

            BookDTO bookDTO = new BookDTO();
            CustomerDTO customerDTO=new CustomerDTO();
            String userResponse="";
            boolean cusExists=true;
            System.out.println("SENDING CUSTOMER REQUEST");
            CustomerSender customerSender = null;
            try {
                customerSender = new CustomerSender();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            if(customerSender!=null)
                userResponse = customerSender.RRFunct(user);
            else
                cusExists=false;

            System.out.println("SENDING BOOK REQUEST");
            String bookResponse = "";
            boolean bookExists=true;
            BookSender bookSender = null;
            try {
                bookSender = new BookSender();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            if(bookSender!=null)
                bookResponse = bookSender.RRFunct(book);
            else
                bookExists=false;

            if(!bookResponse.equals(""))
                bookDTO = gson.fromJson(bookResponse, BookDTO.class);
            if(!userResponse.equals(""))
                customerDTO = gson.fromJson(userResponse, CustomerDTO.class);


            if(!bookExists)
                System.out.println("Book doesnt exist!");
            System.out.println("bookID "+bookDTO.getId().toString());
            if(bookExists&&cusExists)
                bookorderService.addOrder(bookDTO, customerDTO);
            ////////////////////////////////////

        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
