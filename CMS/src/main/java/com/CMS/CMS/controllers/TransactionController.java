package com.CMS.CMS.controllers;

import com.CMS.CMS.dtos.TransactionRequest;
import com.CMS.CMS.dtos.TransactionResponse;
import com.CMS.CMS.services.implementation.TransactionServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@Tag(name = "Transaction", description = "API for managing transactions")
public class TransactionController {

    private final TransactionServiceImplementation transactionServiceImplementation;

    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionServiceImplementation.CreateTransaction(transactionRequest);
    }

    @Operation(summary = "Get transactions by Card ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @GetMapping("/get_by_cardid")
    public List<TransactionResponse> getTransactionByCardid(@RequestParam String cardid) {
        return transactionServiceImplementation.getTransactionsbyCardnb(cardid);
    }
    @Operation(summary = "Get all transactions")
    @ApiResponse(responseCode = "200", description = "List of transactions retrieved successfully")
    @GetMapping("/get_all")
    public List<TransactionResponse> getAllTransactions() {
        return transactionServiceImplementation.getAllTransactions();
    }

    @Operation(summary = "Delete a transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable UUID id) {
        return transactionServiceImplementation.deleteTransaction(id);
    }

    @Operation(summary = "Update a transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found"),
            @ApiResponse(responseCode = "403", description = "Card not active or expired")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable UUID id, @RequestBody TransactionRequest transactionRequest) {
        return transactionServiceImplementation.updateTransaction(id, transactionRequest);
    }
}
