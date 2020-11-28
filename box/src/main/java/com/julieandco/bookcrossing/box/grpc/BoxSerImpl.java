package com.julieandco.bookcrossing.box.grpc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.box.entity.Box;
import com.julieandco.bookcrossing.box.entity.dto.*;
import com.julieandco.bookcrossing.box.service.BoxService;
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
public class BoxSerImpl extends BoxServiceGrpc.BoxServiceImplBase {
    private BoxService boxService;
    @Autowired
    public BoxSerImpl(BoxService boxService) {
        this.boxService = boxService;
    }
    public BoxSerImpl() { }
    @Override
    public void bookCheckIn(
            BoxRequestToAddBook request, StreamObserver<BoxResponseAddedBook> responseObserver) {
        System.out.println("___________________________________________");
        System.out.println("BOOKRADD");
        System.out.println("BOOK= "+request.getBook());
        String info = new StringBuilder()
                .append("Book ")
                .append(request.getBook())
                .append(" is added to the ")
                .append(request.getBox())
                .append(" box")
                .toString();
        ///////////////////////////////////////
        String bookTitle= request.getBook();
        String boxAddress=request.getBox();

        UUID foundBook = null;

        ManagedChannel channel2 = ManagedChannelBuilder.forAddress("localhost", 8011)
                .usePlaintext()
                .build();

        BookProtoServiceGrpc.BookProtoServiceBlockingStub stub2
                = BookProtoServiceGrpc.newBlockingStub(channel2);
        BookResponseGet bResponseGot = stub2.bookRGet(BookRequestToGet.newBuilder()
                .setTitle(bookTitle)
                .build());
        channel2.shutdown();
        if(!bResponseGot.getTitle().equals("null"))
            foundBook=UUID.fromString(bResponseGot.getId());
        GenreDTO g;
        String genre=bResponseGot.getGenre();
        if(genre.equals("Fantasy"))
            g=GenreDTO.Fantasy;
        else if(genre.equals("Thriller"))
            g=GenreDTO.Thriller;
        else g=GenreDTO.Scifi;
        BookDTO bookDTO=new BookDTO(UUID.fromString(bResponseGot.getId()), bResponseGot.getTitle(), bResponseGot.getAuthor(), bResponseGot.getYear(), bResponseGot.getRating(), g);
        Box foundBox = boxService.findByAddress(boxAddress);
        if(foundBook!=null) {
            System.out.println("FOUND BOOK ISNT NULL");
            boxService.addBook(foundBox, bookDTO);
        }
        //////////////////////////////////////
        BoxResponseAddedBook response = BoxResponseAddedBook.newBuilder()
                .setInfo(info)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void bookCheckOut(BoxRequestToGetBook request, StreamObserver<BoxResponseGetBook> responseObserver) {
        String bookTitle = request.getBook();
        String boxAddress=request.getBox();
        //Book foundBook=bookService.findByTitle(bookTitle);

        //BookDTO bookOut = deliver.getBook();
        Box foundBox = boxService.findByAddress(boxAddress);
        UUID foundBook=null;
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
        if(!bResponseGot.getTitle().equals("null"))
            foundBook=UUID.fromString(bResponseGot.getId());
        GenreDTO g;
        String genre=bResponseGot.getGenre();
        if(genre.equals("Fantasy"))
            g=GenreDTO.Fantasy;
        else if(genre.equals("Thriller"))
            g=GenreDTO.Thriller;
        else g=GenreDTO.Scifi;
       BookDTO bookDTO=new BookDTO(UUID.fromString(bResponseGot.getId()), bResponseGot.getTitle(), bResponseGot.getAuthor(), bResponseGot.getYear(), bResponseGot.getRating(), g);
        if(foundBook!=null){
            boxService.checkOut(foundBox, bookDTO);
            System.out.println("FOUNDBOOK IS NOT NULL");
        }
    }

    @Override
    public void boxRAdd(
            BoxRequestToAdd request, StreamObserver<BoxResponseAdded> responseObserver) {
        System.out.println("___________________________________________");
        System.out.println("BOXRADD");
        System.out.println("BOX= "+request.getAddress());
        String info = new StringBuilder()
                .append("Box ")
                .append(request.getAddress())
                .append(" is added to the db")
                .toString();
        String address = request.getAddress();
        Box box=new Box();
        box.setAddress(address);
        boxService.addBox(box);

        BoxResponseAdded response = BoxResponseAdded.newBuilder()
                .setInfo(info)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

