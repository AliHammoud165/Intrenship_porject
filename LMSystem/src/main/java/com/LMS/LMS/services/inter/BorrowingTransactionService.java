package com.LMS.LMS.services.inter;

import com.LMS.LMS.dtos.BorrowingTransactionsBRequest;
import com.LMS.LMS.dtos.BorrowingTransactionsRRequest;
import com.LMS.LMS.models.Book;
import com.LMS.LMS.models.BorrowingTransactions;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface BorrowingTransactionService {
    ResponseEntity<String> createBorrowingTransaction(BorrowingTransactionsBRequest request);

    ResponseEntity<String> returnBorrowedBook(BorrowingTransactionsRRequest request);

    List<Book> getAvailableBooks(String isbn);

    ResponseEntity<String> deleteTransactionById(UUID id);

     List <BorrowingTransactions> getAllTransactionsWithLogs();
}
