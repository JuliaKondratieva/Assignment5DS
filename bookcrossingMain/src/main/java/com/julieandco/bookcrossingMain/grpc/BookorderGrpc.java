package com.julieandco.bookcrossingMain.grpc;

import com.julieandco.bookcrossing.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BookorderGrpc {
    public void postingOrder(String title, String username){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("bookcrossingmediator", 8091)
                .usePlaintext()
                .build();

        OrderServiceGrpc.OrderServiceBlockingStub stub
                = OrderServiceGrpc.newBlockingStub(channel);

        OrderResponseAdded oAdded = stub.orderAdd(OrderRequestToAdd.newBuilder()
                .setBook(title)
                .setCustomer(username)
                .build());
        channel.shutdown();
        System.out.println("RESPONSE: "+oAdded.toString());
    }
}
