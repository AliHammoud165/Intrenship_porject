package com.LMS.LMS.Controllers;

import com.LMS.LMS.DTOs.BookRequest;
import com.LMS.LMS.DTOs.BookUpdateRequest;
import com.LMS.LMS.Entities.Book;
import com.LMS.LMS.Enums.CategoryType;
import com.LMS.LMS.Services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<String> createBook(@RequestBody BookRequest bookRequest){
        return bookService.createBook(bookRequest);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateBook( @RequestBody BookUpdateRequest bookUpdateRequest){
        return bookService.updateBook(bookUpdateRequest);
    }
    @GetMapping ("/get")
    public ResponseEntity<?> getBookById( @RequestParam UUID id){
        return bookService.getBookById(id);
    }
    @GetMapping ("/get_isbn")
    public ResponseEntity<?> getByISBN( @RequestParam String Isbn){
        return bookService.getBookByISBN(Isbn);
    }
    @GetMapping ("/get_all")
    public List<Book> getAllAuthors(){
        return bookService.getAllBook();
    }
    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteBookById( @RequestParam UUID id){
        return bookService.deleteBook(id);
    }

    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String title) {
        return bookService.searchByTitle(title);
    }

    @GetMapping("/search/category")
    public List<Book> searchByCategory(@RequestParam CategoryType category) {
        return bookService.searchByCategory(category);
    }

    @GetMapping("/search/author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return bookService.searchByAuthorName(author);
    }

}
