package com.julieandco.bookcrossing.customer;

import com.julieandco.bookcrossing.customer.grpc.CustomerServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
		/*Server server = ServerBuilder
				.forPort(8080)
				.addService(new CustomerServiceImpl()).build();

		server.start();
		server.awaitTermination();
		SpringApplication.run(CustomerApplication.class, args);*/
	}

}
