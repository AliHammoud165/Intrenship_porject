package com.LMS.LMS.Services;

import com.LMS.LMS.Client.OpenLibraryClient;
import com.LMS.LMS.DTOs.AuthorRequest;
import com.LMS.LMS.DTOs.BookRequest;
import com.LMS.LMS.DTOs.BookUpdateRequest;
import com.LMS.LMS.Entities.Author;
import com.LMS.LMS.Entities.Book;
import com.LMS.LMS.Enums.CategoryType;
import com.LMS.LMS.Repositories.AuthorRepository;
import com.LMS.LMS.Repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
   private final AuthorRepository authorRepository;
   private  final  AuthorService authorService;

    public ResponseEntity<String> createBook(BookRequest bookRequest) {

        List<String> Authors= getAuthorsName(bookRequest);

        List<AuthorRequest> authorRequests = Authors.stream()
                .map(AuthorRequest::new)
                .collect(Collectors.toList());


         authorService.createAuthor(authorRequests.get(0));


        Optional<Author> optionalAuthor = authorRepository.findByNameIgnoreCase(authorRequests.get(0).getName());
        if (optionalAuthor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Author ID");
        }

        Book book = Book.builder()
                .Title(bookRequest.getTitle())
                .ISBN(bookRequest.getISBN())
                .author(optionalAuthor.get())
                .Available(bookRequest.isAvailable())
                .Category(bookRequest.getCategory())
                .RatePrice(bookRequest.getRatePrice())
                .BasePrice(bookRequest.getBasePrice())
                .build();

        if (book.getTitle() == null || book.getISBN() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Title or ISBN required");
        }

        bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book created successfully");
    }


    public ResponseEntity<String> updateBook (BookUpdateRequest request){
        Author author=authorRepository.getAuthorById(request.getAuthorId());
        Book book=bookRepository.getReferenceById(request.getId());
        book.setTitle(request.getTitle());
        book.setISBN(request.getIsbn());
        book.setAvailable(request.isAvailable());
        book.setAuthor(author);
        book.setRatePrice(request.getRatePrice());
        book.setBasePrice(request.getBasePrice());
        bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.OK).body("Book updated successfully");
    }
    public ResponseEntity<?> getBookById(UUID id) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found");
        }

        return ResponseEntity.ok(bookOptional.get());
    }

    public List<Book> getAllBook(){
        return bookRepository.getAllBooks();
    }

    public ResponseEntity<String> deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
        bookRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully");
    }

    public ResponseEntity<?> getBookByISBN(String ISBN) {
        Optional<List<Book>> bookOptional = bookRepository.findByIsbn(ISBN);

        if (bookOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found");
        }

        return ResponseEntity.ok(bookOptional.get());
    }
    public List<Book> searchByTitle(String title) {
        return bookRepository.searchByTitle(title);
    }

    public List<Book> searchByCategory(CategoryType category) {
        return bookRepository.searchByCategory(category);
    }

    public List<Book> searchByAuthorName(String authorName) {
        return bookRepository.searchByAuthorName(authorName);
    }

    private List<String> getAuthorsName (BookRequest book){
        List<String> authors = List.of();
        try {
            authors= OpenLibraryClient.getBookDetailsByISBN(book.getISBN());
        } catch (Exception e) {
            System.err.println("Failed to get authors for ISBN: " + book.getISBN());
            e.printStackTrace();
        }
        return authors;
    }
}
