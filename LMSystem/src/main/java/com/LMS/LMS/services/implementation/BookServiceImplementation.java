package com.LMS.LMS.services.implementation;

import com.LMS.LMS.client.OpenLibraryClient;
import com.LMS.LMS.dtos.AuthorRequest;
import com.LMS.LMS.dtos.BookRequest;
import com.LMS.LMS.dtos.BookUpdateRequest;
import com.LMS.LMS.models.Author;
import com.LMS.LMS.models.Book;
import com.LMS.LMS.enums.CategoryType;
import com.LMS.LMS.models.BorrowingTransactions;
import com.LMS.LMS.repositories.AuthorRepository;
import com.LMS.LMS.repositories.BookRepository;
import com.LMS.LMS.repositories.BorrowingTransactionRepository;
import com.LMS.LMS.services.inter.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorServiceImplementation authorServiceImplementation;
    private final ModelMapper modelMapper;
    private final BorrowingTransactionRepository borrowingTransactionRepository;

    private static final Logger logger =  LoggerFactory.getLogger(BookServiceImplementation.class);

    @Override
    public ResponseEntity<String> createBook(BookRequest bookRequest) {
        logger.info("Creating book with title: {}", bookRequest.getTitle());

        List<String> authors = getAuthorsName(bookRequest);

        List<AuthorRequest> authorRequests = authors.stream()
                .map(AuthorRequest::new)
                .collect(Collectors.toList());

        if (!authorRequests.isEmpty()) {
            logger.info("Creating author '{}' for the book", authorRequests.get(0).getName());
            authorServiceImplementation.createAuthor(authorRequests.get(0));
        } else {
            logger.warn("No authors found for ISBN: {}", bookRequest.getIsbn());
        }

        Optional<Author> optionalAuthor = authorRepository.findByNameIgnoreCase(
                authorRequests.isEmpty() ? "" : authorRequests.get(0).getName());

        if (optionalAuthor.isEmpty()) {
            logger.error("Author not found for book with ISBN: {}", bookRequest.getIsbn());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Author ID");
        }

        Book book = modelMapper.map(bookRequest, Book.class);
        book.setAuthor(optionalAuthor.get());

        if (book.getTitle() == null || book.getIsbn() == null) {
            logger.error("Title or ISBN missing for book creation");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Title or ISBN required");
        }

        bookRepository.save(book);
        logger.info("Book '{}' created successfully", book.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body("Book created successfully");
    }

    @Override
    public ResponseEntity<String> updateBook(BookUpdateRequest request) {
        logger.info("Updating book with id: {}", request.getId());

        Book book = bookRepository.findById(request.getId())
                .orElseThrow(() -> {
                    logger.error("Book with id {} not found", request.getId());
                    return new IllegalArgumentException("Book not found");
                });

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> {
                    logger.error("Author with id {} not found", request.getAuthorId());
                    return new IllegalArgumentException("Author not found");
                });

        modelMapper.map(request, book);
        book.setAuthor(author);

        bookRepository.save(book);
        logger.info("Book with id '{}' updated successfully", book.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Book updated successfully");
    }

    @Override
    public ResponseEntity<?> getBookById(UUID id) {
        logger.info("Fetching book with id: {}", id);

        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isEmpty()) {
            logger.error("Book with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        logger.info("Book with id {} found successfully", id);
        return ResponseEntity.ok(bookOptional.get());
    }

    @Override
    public List<Book> getAllBook() {
        logger.info("Fetching all books");
        return bookRepository.findAll();
    }

    @Override
    public ResponseEntity<String> deleteBook(UUID id) {
        logger.info("Attempting to delete book with id: {}", id);

        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            logger.warn("Book with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        Book book = optionalBook.get();

        List<BorrowingTransactions> transactions = borrowingTransactionRepository.findAllByBookId(id);
        for (BorrowingTransactions transaction : transactions) {
            transaction.setBook(null);
        }
        borrowingTransactionRepository.saveAll(transactions);

        try {
            bookRepository.deleteById(id);
            logger.info("Book with id {} deleted successfully", id);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting book with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting book");
        }
    }

    @Override
    public ResponseEntity<?> getBookByISBN(String ISBN) {
        logger.info("Fetching book by ISBN: {}", ISBN);

        Optional<List<Book>> bookOptional = bookRepository.findAllByIsbn(ISBN);

        if (bookOptional.isEmpty()) {
            logger.error("No books found with ISBN: {}", ISBN);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        logger.info("Books with ISBN {} found successfully", ISBN);
        return ResponseEntity.ok(bookOptional.get());
    }

    @Override
    public List<Book> searchByTitle(String title) {
        logger.info("Searching books by title: {}", title);
        return bookRepository.searchBooksByTitle(title);
    }

    @Override
    public List<Book> searchByCategory(CategoryType category) {
        logger.info("Searching books by category: {}", category);
        return bookRepository.searchBooksByCategory(category);
    }

    @Override
    public List<Book> searchByAuthorName(String authorName) {
        logger.info("Searching books by author name: {}", authorName);
        return bookRepository.searchByAuthorName(authorName);
    }

    @Override
    public List<String> getAuthorsName(BookRequest book) {
        logger.info("Getting authors for book with ISBN: {}", book.getIsbn());

        List<String> authors = List.of();
        try {
            authors = OpenLibraryClient.getBookDetailsByISBN(book.getIsbn());
            logger.info("Authors retrieved successfully for ISBN: {}", book.getIsbn());
        } catch (Exception e) {
            logger.error("Failed to get authors for ISBN: {}", book.getIsbn(), e);
        }
        return authors;
    }
}
