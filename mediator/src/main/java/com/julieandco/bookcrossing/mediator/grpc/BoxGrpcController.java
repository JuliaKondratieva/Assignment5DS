package com.julieandco.bookcrossing.mediator.grpc;

import com.julieandco.bookcrossing.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
@GrpcService
public class BoxGrpcController extends BoxServiceGrpc.BoxServiceImplBase {
    @Override
    public void bookCheckIn(BoxRequestToAddBook request, StreamObserver<BoxResponseAddedBook> responseObserver){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8014)
                .usePlaintext()
                .build();
        BoxServiceGrpc.BoxServiceBlockingStub stub = BoxServiceGrpc.newBlockingStub(channel);
        BoxResponseAddedBook bResponseAdded = stub.bookCheckIn(request);
        channel.shutdown();
        responseObserver.onNext(bResponseAdded);
        responseObserver.onCompleted();
    }

    @Override
    public void bookCheckOut(BoxRequestToGetBook request, StreamObserver<BoxResponseGetBook> responseObserver){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8014)
                .usePlaintext()
                .build();
        BoxServiceGrpc.BoxServiceBlockingStub stub = BoxServiceGrpc.newBlockingStub(channel);
        BoxResponseGetBook bResponseGot = stub.bookCheckOut(request);
        channel.shutdown();
        responseObserver.onNext(bResponseGot);
        responseObserver.onCompleted();
    }

    @Override
    public void boxRAdd(
            BoxRequestToAdd request, StreamObserver<BoxResponseAdded> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8014)
                .usePlaintext()
                .build();
        BoxServiceGrpc.BoxServiceBlockingStub stub = BoxServiceGrpc.newBlockingStub(channel);
        BoxResponseAdded bResponseAdd = stub.boxRAdd(request);
        channel.shutdown();
        responseObserver.onNext(bResponseAdd);
        responseObserver.onCompleted();
    }
}
