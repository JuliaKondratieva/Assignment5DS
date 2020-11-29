package com.julieandco.bookcrossing.mediator.grpc;

import com.julieandco.bookcrossing.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CustomerGrpcController extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void customerAdd(
            UserRequestToAdd request, StreamObserver<UserResponseAdded> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("bookcrossingcust", 8012)
                .usePlaintext()
                .build();
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        UserResponseAdded uResponseAdded = stub.customerAdd(request);
        channel.shutdown();
        responseObserver.onNext(uResponseAdded);
        responseObserver.onCompleted();
    }

    @Override
    public void customerGet(
            UserRequestToGet request, StreamObserver<UserResponseGet> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("bookcrossingcust", 8012)
                .usePlaintext()
                .build();
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        UserResponseGet uResponseGot = stub.customerGet(request);
        channel.shutdown();
        responseObserver.onNext(uResponseGot);
        responseObserver.onCompleted();
    }
}
