package com.julieandco.bookcrossing.bookorder.grpc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.bookorder.entity.dto.*;
import com.julieandco.bookcrossing.bookorder.service.BookorderService;
import com.julieandco.bookcrossing.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@GrpcService
@Service
public class BookorderSerImpl extends OrderServiceGrpc.OrderServiceImplBase {
    private BookorderService bookorderService;

    @Autowired
    public BookorderSerImpl(BookorderService bookorderService) {
        this.bookorderService = bookorderService;
    }

    public BookorderSerImpl() {
    }

    @Override
    public void orderAdd(
            OrderRequestToAdd request, StreamObserver<OrderResponseAdded> responseObserver) {
        System.out.println("___________________________________________");
        System.out.println("BOOKRADD");
        System.out.println("BOOK= " + request.getBook());
        String info = new StringBuilder()
                .append("Book ")
                .append(request.getBook())
                .append(" has been ordered by")
                .append(request.getCustomer())
                .toString();
        String bookTitle = request.getBook();
        String customer = request.getCustomer();

        //////////////////////////////////////////////////////////////////////////////////////
        boolean submitted = false;
        boolean custFound = false;
        CustomerDTO customerDTO=null;
        BookDTO bookDTO=null;

        System.out.println("SENDING CUSTOMER REQUEST");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8012)
                .usePlaintext()
                .build();

        UserServiceGrpc.UserServiceBlockingStub stub
                = UserServiceGrpc.newBlockingStub(channel);
        UserResponseGet uResponseGot = stub.customerGet(UserRequestToGet.newBuilder()
                .setUsername(customer)
                .build());
        channel.shutdown();
        if(!uResponseGot.getUsername().equals("null")) {
            custFound=true;
            customerDTO = new CustomerDTO(UUID.fromString(uResponseGot.getId()), uResponseGot.getUsername());
        }
        System.out.println("SENDING BOOK REQUEST");
        ManagedChannel channel2 = ManagedChannelBuilder.forAddress("localhost", 8011)
                .usePlaintext()
                .build();

        BookProtoServiceGrpc.BookProtoServiceBlockingStub stub2
                = BookProtoServiceGrpc.newBlockingStub(channel2);
        BookResponseGet bResponseGot = stub2.bookRGet(BookRequestToGet.newBuilder()
                .setTitle(bookTitle)
                .build());
        channel2.shutdown();
        GenreDTO g;
        if(bResponseGot.getGenre().equals("Fantasy"))
            g=GenreDTO.Fantasy;
        else if(bResponseGot.getGenre().equals("Thriller"))
            g=GenreDTO.Thriller;
        else g=GenreDTO.Scifi;
        if(!bResponseGot.getTitle().equals("null")) {
            submitted = true;
            bookDTO = new BookDTO(UUID.fromString(bResponseGot.getId()), bResponseGot.getTitle(), bResponseGot.getAuthor(), bResponseGot.getYear(), bResponseGot.getRating(), g);
        }
        //if(!submitted)
          //  System.out.println("Book doesnt exist!");
        //System.out.println("bookID "+bookDTO.getId().toString());
        //if(submitted&&custFound)
        if(submitted&&custFound) {
            bookorderService.addOrder(bookDTO, customerDTO);
        }

        /////////////////////////////////////////////////////////////////////////////////////

        OrderResponseAdded response = OrderResponseAdded.newBuilder()
                .setInfo(info)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}