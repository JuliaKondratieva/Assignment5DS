package com.julieandco.bookcrossing.bookorder.repo;

import com.julieandco.bookcrossing.bookorder.entity.Bookorder;
import com.julieandco.bookcrossing.bookorder.entity.dto.SubmitBookorderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BookorderRepo extends JpaRepository<Bookorder, UUID> {
    @Query("SELECT o FROM Bookorder o WHERE o.bookId = :id")
    List<Bookorder> findByBookId(@Param("id") UUID id);

    //@Query("SELECT o FROM Bookorder o INNER JOIN Book b ON o.book=b.id WHERE b.title = :title")
    //List<Bookorder> findByBookTitle(@Param("title") String title);
}
