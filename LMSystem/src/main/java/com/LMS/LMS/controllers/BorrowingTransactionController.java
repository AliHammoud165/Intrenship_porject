package com.LMS.LMS.controllers;

import com.LMS.LMS.dtos.BorrowingTransactionsBRequest;
import com.LMS.LMS.dtos.BorrowingTransactionsRRequest;
import com.LMS.LMS.models.BorrowingTransactions;
import com.LMS.LMS.services.implementation.BorrowingTransactionServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
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
    }
}
