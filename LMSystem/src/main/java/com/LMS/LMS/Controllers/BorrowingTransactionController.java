package com.LMS.LMS.Controllers;

import com.LMS.LMS.DTOs.BorrowingTransactionsBRequest;
import com.LMS.LMS.DTOs.BorrowingTransactionsRRequest;
import com.LMS.LMS.Services.BorrowingTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrowing_transaction")
@RequiredArgsConstructor
public class BorrowingTransactionController {
    private final BorrowingTransactionService  borrowingTransactionService ;

    @PostMapping("/create")
    public ResponseEntity<String> createBook(@RequestBody BorrowingTransactionsBRequest borrowingTransactionsRequest){
        return borrowingTransactionService.createBorrowingTransaction(borrowingTransactionsRequest);
    }
    @PostMapping("/return")
    public ResponseEntity<String> returnBorrowedBook(@RequestBody BorrowingTransactionsRRequest request) {
        return borrowingTransactionService.returnBorrowedBook(request);
    }
}
