package com.CMS.CMS.controllers;

import com.CMS.CMS.DTOs.TransactionRequest;
import com.CMS.CMS.DTOs.TransactionRespond;
import com.CMS.CMS.Services.TransactionService;
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

    private final TransactionService transactionService;

    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionService.CreateTransaction(transactionRequest);
        return ResponseEntity.status(201).body("Transaction created successfully");
    }

    @Operation(summary = "Get transactions by Card ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @GetMapping("/get_by_cardid")
    public List<TransactionRespond> getTransactionByCardid(@RequestParam String cardid) {
        return transactionService.getTransactionsbyCardnb(cardid);
    }
}
