package com.julieandco.bookcrossing.book.grpc;

import com.julieandco.bookcrossing.book.entity.Book;
import com.julieandco.bookcrossing.book.entity.Genre;
import com.julieandco.bookcrossing.book.service.BookService;
import com.julieandco.bookcrossing.grpc.BookProtoServiceGrpc;
import com.julieandco.bookcrossing.grpc.BookRequestToAdd;
import com.julieandco.bookcrossing.grpc.BookResponseAdded;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import com.julieandco.bookcrossing.book.grpc.StaticApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class BookSerImpl extends BookProtoServiceGrpc.BookProtoServiceImplBase {
   // private BookService bookService;
    //@Autowired
    //public BookSerImpl(BookService bookService) {
    //this.bookService = bookService;
    //}
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
        ((BookService)StaticApplicationContext.getContext().getBean("BookService")).addBook(book);
        //bookService.addBook(book);

        BookResponseAdded response = BookResponseAdded.newBuilder()
                .setInfo(info)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
