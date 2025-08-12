package com.LMS.LMS.controllers;

import com.LMS.LMS.dtos.BookRequest;
import com.LMS.LMS.dtos.BookUpdateRequest;
import com.LMS.LMS.models.Book;
import com.LMS.LMS.enums.CategoryType;
import com.LMS.LMS.services.implementation.BookServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImplementation bookServiceImplementation;

    @PostMapping("/create")
    public ResponseEntity<String> createBook(@RequestBody BookRequest bookRequest){
        return bookServiceImplementation.createBook(bookRequest);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateBook( @RequestBody BookUpdateRequest bookUpdateRequest){
        return bookServiceImplementation.updateBook(bookUpdateRequest);
    }
    @GetMapping ("/get/{id}")
    public ResponseEntity<?> getBookById( @PathVariable UUID id){
        return bookServiceImplementation.getBookById(id);
    }
    @GetMapping ("/get_isbn")
    public ResponseEntity<?> getByISBN( @RequestParam String Isbn){
        return bookServiceImplementation.getBookByISBN(Isbn);
    }
    @GetMapping ("/get_all")
    public List<Book> getAllAuthors(){
        return bookServiceImplementation.getAllBook();
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteBookById( @PathVariable UUID id){
        return bookServiceImplementation.deleteBook(id);
    }

    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String title) {
        return bookServiceImplementation.searchByTitle(title);
    }

    @GetMapping("/search/category")
    public List<Book> searchByCategory(@RequestParam CategoryType category) {
        return bookServiceImplementation.searchByCategory(category);
    }

    @GetMapping("/search/author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return bookServiceImplementation.searchByAuthorName(author);
    }

}
