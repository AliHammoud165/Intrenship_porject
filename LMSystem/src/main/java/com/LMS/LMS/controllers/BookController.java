package com.LMS.LMS.controllers;

import com.LMS.LMS.dtos.BookRequest;
import com.LMS.LMS.dtos.BookUpdateRequest;
import com.LMS.LMS.enums.CategoryType;
import com.LMS.LMS.models.Book;
import com.LMS.LMS.services.implementation.BookServiceImplementation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@Tag(name = "Book", description = "API for managing books")
public class BookController {

    private final BookServiceImplementation bookServiceImplementation;

    @Operation(summary = "Create a new book", description = "Create a book with the given details and associated author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "403", description = "Missing title or ISBN"),
            @ApiResponse(responseCode = "400", description = "Invalid author ID or bad request")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createBook(
            @Parameter(description = "Book creation request", required = true)
            @RequestBody BookRequest bookRequest) {
        return bookServiceImplementation.createBook(bookRequest);
    }

    @Operation(summary = "Update an existing book", description = "Update book details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book or Author not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/update")
    public ResponseEntity<String> updateBook(
            @Parameter(description = "Book update request with ID and new details", required = true)
            @RequestBody BookUpdateRequest bookUpdateRequest) {
        return bookServiceImplementation.updateBook(bookUpdateRequest);
    }

    @Operation(summary = "Get a book by ID", description = "Retrieve a single book by its UUID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
            ),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBookById(
            @Parameter(description = "UUID of the book", required = true)
            @PathVariable UUID id) {
        return bookServiceImplementation.getBookById(id);
    }

    @Operation(summary = "Get books by ISBN", description = "Retrieve list of books by their ISBN number")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Books retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
            ),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/get_isbn")
    public ResponseEntity<?> getByISBN(
            @Parameter(description = "ISBN string to search for", required = true)
            @RequestParam String Isbn) {
        return bookServiceImplementation.getBookByISBN(Isbn);
    }

    @Operation(summary = "Get all books", description = "Retrieve all books in the system")
    @ApiResponse(
            responseCode = "200",
            description = "List of books returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
    )
    @GetMapping("/get_all")
    public List<Book> getAllBooks() {
        return bookServiceImplementation.getAllBook();
    }

    @Operation(summary = "Delete a book by ID", description = "Delete a book given its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookById(
            @Parameter(description = "UUID of the book to delete", required = true)
            @PathVariable UUID id) {
        return bookServiceImplementation.deleteBook(id);
    }

    @Operation(summary = "Search books by title", description = "Find books with matching titles")
    @ApiResponse(
            responseCode = "200",
            description = "Books matching title returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
    )
    @GetMapping("/search/title")
    public List<Book> searchByTitle(
            @Parameter(description = "Title to search for", required = true)
            @RequestParam String title) {
        return bookServiceImplementation.searchByTitle(title);
    }

    @Operation(summary = "Search books by category", description = "Find books matching a specific category")
    @ApiResponse(
            responseCode = "200",
            description = "Books matching category returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
    )
    @GetMapping("/search/category")
    public List<Book> searchByCategory(
            @Parameter(description = "Category to search for", required = true)
            @RequestParam CategoryType category) {
        return bookServiceImplementation.searchByCategory(category);
    }

    @Operation(summary = "Search books by author name", description = "Find books by matching author names")
    @ApiResponse(
            responseCode = "200",
            description = "Books matching author returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
    )
    @GetMapping("/search/author")
    public List<Book> searchByAuthor(
            @Parameter(description = "Author name to search for", required = true)
            @RequestParam String author) {
        return bookServiceImplementation.searchByAuthorName(author);
    }
}
