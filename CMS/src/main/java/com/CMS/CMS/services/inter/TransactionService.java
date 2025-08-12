package com.CMS.CMS.services.inter;

import com.CMS.CMS.dtos.TransactionRequest;
import com.CMS.CMS.dtos.TransactionResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    ResponseEntity<String> CreateTransaction(TransactionRequest transactionRequest);

    boolean validate(TransactionRequest transaction);

    List<TransactionResponse> getTransactionsbyCardnb(String cardNb);

    List<TransactionResponse> getAllTransactions();

    ResponseEntity<String> deleteTransaction(UUID id);

    ResponseEntity<String> updateTransaction(UUID id, TransactionRequest updateRequest);
}
