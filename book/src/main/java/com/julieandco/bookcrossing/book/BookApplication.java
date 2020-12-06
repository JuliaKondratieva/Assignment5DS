package com.julieandco.bookcrossing.book;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.book.entity.Book;
import com.julieandco.bookcrossing.book.entity.Genre;
import com.julieandco.bookcrossing.book.entity.dto.BookDTO;
import com.julieandco.bookcrossing.book.grpc.BookSerImpl;
import com.julieandco.bookcrossing.book.rabbitmq.BookReceiver;
import com.julieandco.bookcrossing.book.service.BookService;
import com.rabbitmq.client.DeliverCallback;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@SpringBootApplication
public class BookApplication {
	//private final static String QUEUE_NAME = "bookqueue";
	//@Autowired
	//private static BookReceiver bookReceiver;
	public static void main(String[] argv) {
		SpringApplication.run(BookApplication.class, argv);
		/*ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + message + "'");
			bookReceiver.BookPost(message);
		};
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });*/
	}
}
	//public static void main(String[] args){
	//	SpringApplication.run(BookApplication.class, args);
	//}
//}
		//{SpringApplication.run(BookApplication.class, args);
		/*throws IOException, InterruptedException {

		Server server = ServerBuilder
				.forPort(8001)
				.addService(new BookSerImpl()).build();

		server.start();
		server.awaitTermination();
	}*/

