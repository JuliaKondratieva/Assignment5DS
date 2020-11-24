package com.julieandco.bookcrossing.book.controller;

import com.julieandco.bookcrossing.book.entity.Book;
import com.julieandco.bookcrossing.book.entity.Genre;
import com.julieandco.bookcrossing.book.entity.dto.BookDTO;
import com.julieandco.bookcrossing.book.entity.dto.BooksDTO;
import com.julieandco.bookcrossing.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public @ResponseBody
    BooksDTO getAllBooks(){
        BooksDTO booksDTO = new BooksDTO();
        List<Book> books = bookService.getAllBooks();
        for(Book b: books){
            System.out.println("BID: "+b.getId().toString());
            BookDTO bookDTO = new BookDTO(b.getId(), b.getTitle(), b.getAuthor(), b.getYear(), b.getRating(), b.getGenre());
            booksDTO.getBooks().add(bookDTO);
        }
        return booksDTO;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveBook(@RequestBody BookDTO bookDTO){
        String title = bookDTO.getTitle();
        String author=bookDTO.getAuthor();
        Genre genre = bookDTO.getGenre();
        long year =bookDTO.getYear();
        double rating=bookDTO.getRating();
        Book newBook = new Book(title, author, year, rating, genre);
        bookService.addBook(newBook);

        return ResponseEntity.ok().build();
    }
}
