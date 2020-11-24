package com.julieandco.bookcrossing.book;

import com.julieandco.bookcrossing.book.grpc.BookSerImpl;
import com.julieandco.bookcrossing.book.service.BookService;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class BookApplication {

	public static void main(String[] args)
		//{SpringApplication.run(BookApplication.class, args);
		throws IOException, InterruptedException {

		Server server = ServerBuilder
				.forPort(8001)
				.addService(new BookSerImpl()).build();

		server.start();
		server.awaitTermination();
	}
}
