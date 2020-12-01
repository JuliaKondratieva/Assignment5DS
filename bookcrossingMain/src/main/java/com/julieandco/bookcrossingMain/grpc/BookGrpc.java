package com.julieandco.bookcrossingMain.grpc;

import com.julieandco.bookcrossing.grpc.BookProtoServiceGrpc;
import com.julieandco.bookcrossing.grpc.BookRequestToAdd;
import com.julieandco.bookcrossing.grpc.BookResponseAdded;
import com.julieandco.bookcrossingMain.entity.Genre;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BookGrpc {
public void postingBook(String title, String author, int year, float rating, Genre g){
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8888)
            .usePlaintext()
            .build();

    BookProtoServiceGrpc.BookProtoServiceBlockingStub stub
            = BookProtoServiceGrpc.newBlockingStub(channel);

    BookResponseAdded bAdded = stub.bookRAdd(BookRequestToAdd.newBuilder()
            .setTitle(title)
            .setAuthor(author)
            .setYear(year)
            .setRating(rating)
            .setGenre(g.toString())
            .build());
    channel.shutdown();
    System.out.println("RESPONSE: "+bAdded.toString());
}
}

