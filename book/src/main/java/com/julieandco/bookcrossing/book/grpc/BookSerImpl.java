package com.julieandco.bookcrossing.book.grpc;

import com.julieandco.bookcrossing.book.entity.Book;
import com.julieandco.bookcrossing.book.entity.Genre;
import com.julieandco.bookcrossing.book.service.BookService;
import com.julieandco.bookcrossing.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GrpcService
@Service
public class BookSerImpl extends BookProtoServiceGrpc.BookProtoServiceImplBase {
    private BookService bookService;
   @Autowired
   public BookSerImpl(BookService bookService) {
   this.bookService = bookService;
   }
    public BookSerImpl() { }
    @Override
    public void bookRAdd(
            BookRequestToAdd request, StreamObserver<BookResponseAdded> responseObserver) {
        System.out.println("___________________________________________");
        System.out.println("BOOKRADD");
        System.out.println("BOOK= "+request.getTitle());
        String info = new StringBuilder()
                .append("Book ")
                .append(request.getTitle())
                .append(" is added to the db")
                .toString();
        String bookTitle = request.getTitle();
        String author=request.getAuthor();
        String genre=request.getGenre();
        Genre g;
        int year=request.getYear();
        double rating = request.getRating();
        if(genre.equals("Fantasy"))
            g=Genre.Fantasy;
        else if(genre.equals("Thriller"))
            g=Genre.Thriller;
        else g=Genre.Scifi;
        Book book=new Book(bookTitle, author, year, rating, g);
        bookService.addBook(book);

        BookResponseAdded response = BookResponseAdded.newBuilder()
                .setInfo(info)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void bookRGet(BookRequestToGet request, StreamObserver<BookResponseGet> responseObserver) {
        String bookTitle = request.getTitle();
        Book foundBook=bookService.findByTitle(bookTitle);
        BookResponseGet response;
        if(foundBook==null)
        {
            response = BookResponseGet.newBuilder()
                    .setTitle("null")
                    .setAuthor("null")
                    .setGenre("null")
                    .setYear(0)
                    .setRating(0)
                    .setId("null")
                    .build();
        }
        else
        {
            response = BookResponseGet.newBuilder()
                    .setTitle(foundBook.getTitle())
                    .setAuthor(foundBook.getAuthor())
                    .setGenre(foundBook.getGenre().toString())
                    .setYear(foundBook.getYear())
                    .setRating(foundBook.getRating())
                    .setId(foundBook.getId().toString())
                    .build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
