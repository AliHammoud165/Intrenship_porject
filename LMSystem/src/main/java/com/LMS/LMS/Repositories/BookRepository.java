package com.LMS.LMS.Repositories;

import com.LMS.LMS.Entities.Book;
import com.LMS.LMS.Enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Book getById(UUID id);
    @Query("SELECT b FROM Book b ")
    List<Book> getAllBooks();

    @Query("SELECT b FROM Book b WHERE b.ISBN = :isbn")
    Optional<List<Book>> findByIsbn(@Param("isbn") String isbn);

    @Query("SELECT b FROM Book b WHERE b.ISBN = :isbn AND b.Available = true")
    List<Book> findByIsbnAndAvailableTrue(@Param("isbn") String isbn);
    @Query("SELECT b FROM Book b WHERE LOWER(b.Title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> searchByTitle(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE b.Category = :category")
    List<Book> searchByCategory(@Param("category") CategoryType category);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author.Name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<Book> searchByAuthorName(@Param("authorName") String authorName);

}




