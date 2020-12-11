package com.julieandco.bookcrossing.book.service;

import com.julieandco.bookcrossing.book.entity.Book;
import com.julieandco.bookcrossing.book.entity.dto.BookDTO;
import com.julieandco.bookcrossing.book.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private final BookRepo bookRepository;

    @Autowired
    public BookService(BookRepo bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    @Transactional
    public Book findByTitle(String title){
        return bookRepository.findBookByTitle(title);
    }

    @Async
    @Transactional
    public void addBook(Book book){
        if(bookRepository.findBookByTitle(book.getTitle()) == null){
            System.out.println("SUPPOSED TO ADD TO SERVICE");
            bookRepository.save(book);
        }
    }
}
