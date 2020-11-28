package com.julieandco.bookcrossing.mediator.grpc;

import com.julieandco.bookcrossing.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BookGrpcController extends BookProtoServiceGrpc.BookProtoServiceImplBase {
    @Override
    public void bookRGet(BookRequestToGet request, StreamObserver<BookResponseGet> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8011)
                .usePlaintext()
                .build();
        BookProtoServiceGrpc.BookProtoServiceBlockingStub stub = BookProtoServiceGrpc.newBlockingStub(channel);
        BookResponseGet bResponseGot = stub.bookRGet(request);
        channel.shutdown();
        responseObserver.onNext(bResponseGot);
        responseObserver.onCompleted();
    }

    @Override
    public void bookRAdd(BookRequestToAdd request, StreamObserver<BookResponseAdded> responseObserver){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8011)
                .usePlaintext()
                .build();
        BookProtoServiceGrpc.BookProtoServiceBlockingStub stub = BookProtoServiceGrpc.newBlockingStub(channel);
        BookResponseAdded bResponseAdded = stub.bookRAdd(request);
        channel.shutdown();
        responseObserver.onNext(bResponseAdded);
        responseObserver.onCompleted();
    }
}
