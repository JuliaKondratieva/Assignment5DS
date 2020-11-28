package com.julieandco.bookcrossing.box.repo;

import com.julieandco.bookcrossing.box.entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BoxRepo extends JpaRepository<Box, UUID> {
    @Query("SELECT b FROM Box b WHERE b.address = :address")
    Box findByAddress(@Param("address") String address);

    @Query("SELECT b FROM Box b WHERE b.books=:books")
    Box findByBook(@Param("books") UUID books);
}
