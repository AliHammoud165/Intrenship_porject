package com.LMS.LMS.services.inter;

import com.LMS.LMS.dtos.BookRequest;
import com.LMS.LMS.dtos.BookUpdateRequest;
import com.LMS.LMS.enums.CategoryType;
import com.LMS.LMS.models.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface BookService {
    ResponseEntity<String> createBook(BookRequest bookRequest);

    ResponseEntity<String> updateBook(BookUpdateRequest request);

    ResponseEntity<?> getBookById(UUID id);

    List<Book> getAllBook();

    ResponseEntity<String> deleteBook(UUID id);

    ResponseEntity<?> getBookByISBN(String ISBN);

    List<Book> searchByTitle(String title);

    List<Book> searchByCategory(CategoryType category);

    List<Book> searchByAuthorName(String authorName);

    List<String> getAuthorsName(BookRequest book);
}
