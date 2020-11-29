package com.julieandco.bookcrossingMain.grpc;

import com.julieandco.bookcrossing.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BoxGrpc {
    public void postingBox(String address){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("bookcrossingmediator", 8091)
                .usePlaintext()
                .build();

        BoxServiceGrpc.BoxServiceBlockingStub stub
                = BoxServiceGrpc.newBlockingStub(channel);

        BoxResponseAdded bAdded = stub.boxRAdd(BoxRequestToAdd.newBuilder()
                .setAddress(address)
                .build());
        channel.shutdown();
        System.out.println("RESPONSE: "+bAdded.toString());
    }

    public void CheckIn(String book, String box){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("bookcrossingmediator", 8091)
                .usePlaintext()
                .build();

        BoxServiceGrpc.BoxServiceBlockingStub stub
                = BoxServiceGrpc.newBlockingStub(channel);

        BoxResponseAddedBook bAdded = stub.bookCheckIn(BoxRequestToAddBook.newBuilder()
                .setBook(book)
                .setBox(box)
                .build());
        channel.shutdown();
        System.out.println("RESPONSE: "+bAdded.toString());
    }

    public void CheckOut(String book, String box){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("bookcrossingmediator", 8091)
                .usePlaintext()
                .build();

        BoxServiceGrpc.BoxServiceBlockingStub stub
                = BoxServiceGrpc.newBlockingStub(channel);

        BoxResponseGetBook bAdded = stub.bookCheckOut(BoxRequestToGetBook.newBuilder()
                .setBook(book)
                .setBox(box)
                .build());
        channel.shutdown();
        System.out.println("RESPONSE: "+bAdded.toString());
    }
}
