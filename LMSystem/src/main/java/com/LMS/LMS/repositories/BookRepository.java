package com.LMS.LMS.repositories;

import com.LMS.LMS.models.Book;
import com.LMS.LMS.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Book getById(UUID id);

    Optional<List<Book>> findAllByIsbn(@Param("isbn") String isbn);

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn AND b.available = true")
    List<Book> findByIsbnAndAvailableTrue(@Param("isbn") String isbn);

    List<Book> searchBooksByTitle(@Param("title") String title);

    List<Book> searchBooksByCategory(@Param("category") CategoryType category);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<Book> searchByAuthorName(@Param("authorName") String authorName);

}




