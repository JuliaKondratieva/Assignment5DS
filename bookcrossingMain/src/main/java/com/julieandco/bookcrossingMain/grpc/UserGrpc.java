package com.julieandco.bookcrossingMain.grpc;

import com.julieandco.bookcrossing.grpc.*;
import com.julieandco.bookcrossingMain.entity.Genre;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class UserGrpc {
    public void postingUser(String username){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8091)
                .usePlaintext()
                .build();

        UserServiceGrpc.UserServiceBlockingStub stub
                = UserServiceGrpc.newBlockingStub(channel);

        UserResponseAdded uAdded = stub.customerAdd(UserRequestToAdd.newBuilder()
                .setUsername(username)
                .build());
        channel.shutdown();
        System.out.println("RESPONSE: "+uAdded.toString());
    }
}
