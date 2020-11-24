package com.julieandco.bookcrossing.book.repo;

import com.julieandco.bookcrossing.book.entity.Book;
import com.julieandco.bookcrossing.book.entity.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BookRepo extends JpaRepository<Book, UUID>{
    @Query("SELECT b FROM Book b WHERE b.title = :title")
    Book findBookByTitle(String title);
}
