package com.LMS.LMS.controllers;

import com.LMS.LMS.dtos.BorrowingTransactionsBRequest;
import com.LMS.LMS.dtos.BorrowingTransactionsRRequest;
<<<<<<< HEAD
import com.LMS.LMS.models.Book;
import com.LMS.LMS.models.BorrowingTransactions;
import com.LMS.LMS.services.implementation.BorrowingTransactionServiceImplementation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
=======
import com.LMS.LMS.models.BorrowingTransactions;
import com.LMS.LMS.services.implementation.BorrowingTransactionServiceImplementation;
>>>>>>> d599a845c61de5c926be53327c33faca6a6cf504
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
<<<<<<< HEAD
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@Tag(name = "Borrowing Transaction", description = "API for borrowing and returning books")
public class BorrowingTransactionController {

    private final BorrowingTransactionServiceImplementation transactionService;

    @Operation(summary = "Create a borrowing transaction", description = "Borrow a book for a borrower")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Borrowing transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Missing borrow date or status"),
            @ApiResponse(responseCode = "404", description = "Book or Borrower not found or no available book copy"),
    })
    @PostMapping("/borrow")
    public ResponseEntity<String> createBorrowingTransaction(
            @Parameter(description = "Borrowing transaction creation request", required = true)
            @RequestBody BorrowingTransactionsBRequest request) {
        return transactionService.createBorrowingTransaction(request);
    }

    @Operation(summary = "Return a borrowed book", description = "Return a previously borrowed book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book returned successfully"),
            @ApiResponse(responseCode = "404", description = "Book or Borrower not found, or active borrowing transaction not found"),
    })
    @PutMapping("/return")
    public ResponseEntity<String> returnBorrowedBook(
            @Parameter(description = "Return book request", required = true)
            @RequestBody BorrowingTransactionsRRequest request) {
        return transactionService.returnBorrowedBook(request);
    }

    @Operation(summary = "Get available books by ISBN", description = "List all available copies of a book by ISBN")
    @ApiResponse(responseCode = "200", description = "List of available books",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @GetMapping("/available/{isbn}")
    public List<Book> getAvailableBooks(
            @Parameter(description = "ISBN of the book", required = true)
            @PathVariable String isbn) {
        return transactionService.getAvailableBooks(isbn);
    }

    @Operation(summary = "Delete a borrowing transaction by ID", description = "Delete a transaction record by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found"),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTransactionById(
            @Parameter(description = "UUID of the transaction to delete", required = true)
            @PathVariable UUID id) {
        return transactionService.deleteTransactionById(id);
    }

    @Operation(summary = "Get all borrowing transactions", description = "Retrieve all borrowing transactions with logs")
    @ApiResponse(responseCode = "200", description = "List of borrowing transactions",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BorrowingTransactions.class)))
    @GetMapping("/all")
    public List<BorrowingTransactions> getAllTransactionsWithLogs() {
        return transactionService.getAllTransactionsWithLogs();
=======
@RequestMapping("/api/borrowing_transaction")
@RequiredArgsConstructor
public class BorrowingTransactionController {
    private final BorrowingTransactionServiceImplementation borrowingTransactionServiceImplementation;

    @PostMapping("/create")
    public ResponseEntity<String> createBook(@RequestBody BorrowingTransactionsBRequest borrowingTransactionsRequest){
        return borrowingTransactionServiceImplementation.createBorrowingTransaction(borrowingTransactionsRequest);
    }
    @PostMapping("/return")
    public ResponseEntity<String> returnBorrowedBook(@RequestBody BorrowingTransactionsRRequest request) {
        return borrowingTransactionServiceImplementation.returnBorrowedBook(request);
    }
    @GetMapping("/get_all")
    public List<BorrowingTransactions> getAllTransactions() {
        return borrowingTransactionServiceImplementation.getAllTransactionsWithLogs();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable UUID id) {
        return borrowingTransactionServiceImplementation.deleteTransactionById(id);
>>>>>>> d599a845c61de5c926be53327c33faca6a6cf504
    }
}
