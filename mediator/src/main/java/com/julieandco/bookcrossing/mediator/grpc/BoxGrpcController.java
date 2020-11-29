package com.julieandco.bookcrossing.mediator.grpc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.grpc.*;
import com.julieandco.bookcrossing.mediator.dto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

@GrpcService
public class BoxGrpcController extends BoxServiceGrpc.BoxServiceImplBase {
    @Override
    public void bookCheckIn(BoxRequestToAddBook request, StreamObserver<BoxResponseAddedBook> responseObserver){
        ////////////////////////////////////////////////////
        System.out.println("DELIVERPRODUCTS FUNCTION");
        String box = request.getBox();
        String book = request.getBook();

        System.out.println("---SENDING REQUEST TO BOXES-----");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("bookcrossingbox", 8014)
                .usePlaintext()
                .build();
        BoxServiceGrpc.BoxServiceBlockingStub stub = BoxServiceGrpc.newBlockingStub(channel);
        BoxResponseAddedBook bResponseAdded = stub.bookCheckIn(request);
        channel.shutdown();
        responseObserver.onNext(bResponseAdded);
        responseObserver.onCompleted();


        System.out.println("SENDING REQUEST TO BOOKS");
        ManagedChannel channel3 = ManagedChannelBuilder.forAddress("bookcrossingbook", 8011)
                .usePlaintext()
                .build();
        BookProtoServiceGrpc.BookProtoServiceBlockingStub stub3 = BookProtoServiceGrpc.newBlockingStub(channel3);
        BookResponseGet bGet = stub3.bookRGet(BookRequestToGet.newBuilder()
                .setTitle(request.getBook())
                .build());
        channel3.shutdown();


        SubmitBookorderDTO foundOrder = new SubmitBookorderDTO();
        System.out.println("SENDING REQUEST TO BOOKORDERS");
        ManagedChannel channel2 = ManagedChannelBuilder.forAddress("bookcrossingorder", 8013)
                .usePlaintext()
                .build();
        OrderServiceGrpc.OrderServiceBlockingStub stub2 = OrderServiceGrpc.newBlockingStub(channel2);
        OrderResponseGet oResponseGet = stub2.orderGet(OrderRequestToGet.newBuilder()
                .setBook(bGet.getId())
                .build());
        channel2.shutdown();

        if(oResponseGet.getDelivered()){
            System.out.println("DELIVERY STATE=true");
            //delete order and next in order set
            ManagedChannel channel4 = ManagedChannelBuilder.forAddress("bookcrossingorder", 8013)
                    .usePlaintext()
                    .build();
            OrderServiceGrpc.OrderServiceBlockingStub stub4 = OrderServiceGrpc.newBlockingStub(channel4);
            OrderResponseDeleted oResponseDel = stub4.orderDelete(OrderRequestToDelete.newBuilder()
                    .setBook(bGet.getId())
                    .build());
            channel4.shutdown();
        }
        else {
            System.out.println("DELIVERY STATE=false");
            ManagedChannel channel5 = ManagedChannelBuilder.forAddress("bookcrossingorder", 8013)
                    .usePlaintext()
                    .build();
            OrderServiceGrpc.OrderServiceBlockingStub stub5 = OrderServiceGrpc.newBlockingStub(channel5);
            OrderResponseUpdated oResponseDel = stub5.orderUpdate(OrderRequestToUpdate.newBuilder()
                    .setBook(bGet.getId())
                    .build());
            channel5.shutdown();

            System.out.println("SENT RESPONSE");
        }
        ///////////////////////////////////////////////////

    }

    @Override
    public void bookCheckOut(BoxRequestToGetBook request, StreamObserver<BoxResponseGetBook> responseObserver){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("bookcrossingbox", 8014)
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
            BoxRequestToAdd request, StreamObserver<BoxResponseAdded> responseObserver){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("bookcrossingbox", 8014)
                .usePlaintext()
                .build();
        BoxServiceGrpc.BoxServiceBlockingStub stub = BoxServiceGrpc.newBlockingStub(channel);
        BoxResponseAdded bResponseAdded = stub.boxRAdd(request);
        channel.shutdown();
        responseObserver.onNext(bResponseAdded);
        responseObserver.onCompleted();
    }
}
