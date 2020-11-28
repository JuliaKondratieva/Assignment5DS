package com.julieandco.bookcrossing.mediator.grpc;

import com.julieandco.bookcrossing.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BookorderGrpcController extends OrderServiceGrpc.OrderServiceImplBase {
    @Override
    public void orderAdd(OrderRequestToAdd request, StreamObserver<OrderResponseAdded> responseObserver){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8013)
                .usePlaintext()
                .build();
        OrderServiceGrpc.OrderServiceBlockingStub stub = OrderServiceGrpc.newBlockingStub(channel);
        OrderResponseAdded oResponseAdded = stub.orderAdd(request);
        channel.shutdown();
        responseObserver.onNext(oResponseAdded);
        responseObserver.onCompleted();
    }

    @Override
    public void orderGet(OrderRequestToGet request, StreamObserver<OrderResponseGet> responseObserver){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8013)
                .usePlaintext()
                .build();
        OrderServiceGrpc.OrderServiceBlockingStub stub = OrderServiceGrpc.newBlockingStub(channel);
        OrderResponseGet oResponseGot = stub.orderGet(request);
        channel.shutdown();
        responseObserver.onNext(oResponseGot);
        responseObserver.onCompleted();
    }
}
