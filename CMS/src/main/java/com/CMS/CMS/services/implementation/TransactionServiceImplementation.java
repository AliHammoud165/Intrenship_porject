package com.CMS.CMS.services.implementation;

import com.CMS.CMS.dtos.AccountResponse;
import com.CMS.CMS.dtos.BalanceUpdateRequest;
import com.CMS.CMS.dtos.TransactionRequest;
import com.CMS.CMS.dtos.TransactionResponse;
import com.CMS.CMS.models.Card;
import com.CMS.CMS.models.CardAccount;
import com.CMS.CMS.models.Transaction;
import com.CMS.CMS.enums.StatusType;
import com.CMS.CMS.enums.TransactionType;
import com.CMS.CMS.repositories.AccountRepository;
import com.CMS.CMS.repositories.CardAccountRepository;
import com.CMS.CMS.repositories.CardRepository;
import com.CMS.CMS.repositories.TransactionRepository;
import com.CMS.CMS.services.inter.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService {

    private final ModelMapper modelMapper;
    private final TransactionRepository transactionRepository;
    private final AccountServiceImplementation accountServiceImplementation;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CardServiceImplementation cardService;
    private final CardAccountServiceImplementation cardAccountServiceImplementation;
    private final CardAccountRepository cardAccountRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImplementation.class);

    @Override
    public ResponseEntity<String> CreateTransaction(TransactionRequest transactionRequest) {
        logger.info("Attempting to create transaction for card number: {}", transactionRequest.getCardNb());

        Card card = cardRepository.findCardByCardNumber(transactionRequest.getCardNb());//getting the card by card Nb

        if (validate(transactionRequest)) {
            Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
            transaction.setCard(card);
            transaction.setTransactionDate(LocalDateTime.now());
            transactionRepository.save(transaction);
            logger.info("Transaction created successfully for card number: {}", transactionRequest.getCardNb());
            return ResponseEntity.status(HttpStatus.CREATED).body("Transaction successfully");
        } else {
            logger.warn("Invalid transaction request for card number: {}", transactionRequest.getCardNb());
            return ResponseEntity.badRequest().body("Invalid Transaction");
        }
    }

    @Override
    public boolean validate(TransactionRequest transaction) {
        logger.debug("Validating transaction for card number: {}", transaction.getCardNb());

        Card card = cardRepository.findCardByCardNumber(transaction.getCardNb());
        if (card == null) {
            logger.error("Validation failed - Card not found with number: {}", transaction.getCardNb());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }

        List<CardAccount> cardAccounts = cardAccountRepository.getCardAccountByCard_Id(card.getId());
        List<CardAccount> filterAccounts = cardAccounts.stream()
                .filter(ca -> ca.getAccount().getCurrency() == transaction.getCurrencyType())
                .toList();

        if (filterAccounts.isEmpty()) {
            logger.error("No linked accounts found with matching currency for card ID: {}", card.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No matching currency account linked to card");
        }


        if (card.getStatus() != StatusType.ACTIVE) {
            logger.error("Validation failed - Card is not active. Card ID: {}", card.getId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Card not active");
        }

        if (card.getExpiryDate().isBefore(LocalDate.now())) {
            logger.error("Validation failed - Card expired. Card ID: {}", card.getId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Card expired");
        }

        if (filterAccounts.get(0).getAccount().getStatus() != StatusType.ACTIVE) {
            logger.error("Validation failed - Account not active");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account not active");
        }

        if (filterAccounts.get(0).getAccount().getBalance().compareTo(transaction.getTransactionAmount()) < 0
                && transaction.getTransactionType() == TransactionType.D) {
            logger.error("Validation failed - Insufficient balance for debit");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dont have enough balance");
        }

        if (transaction.getTransactionType() == TransactionType.D) {
            BigDecimal newBalance = filterAccounts.get(0).getAccount().getBalance().subtract(transaction.getTransactionAmount());
            accountServiceImplementation.updatebalance(
                    new BalanceUpdateRequest(filterAccounts.get(0).getAccount().getId(), newBalance));
            logger.info("Debited {}  New balance: {}",
                    transaction.getTransactionAmount(),  newBalance);
        } else if (transaction.getTransactionType() == TransactionType.C) {
            BigDecimal newBalance = filterAccounts.get(0).getAccount().getBalance().add(transaction.getTransactionAmount());
            accountServiceImplementation.updatebalance(
                    new BalanceUpdateRequest(filterAccounts.get(0).getAccount().getId(), newBalance));
            logger.info("Credited {} New balance: {}",
                    transaction.getTransactionAmount(), newBalance);
        }

        logger.debug("Transaction validated successfully for card number: {}", transaction.getCardNb());
        return true;
    }

    @Override
    public List<TransactionResponse> getTransactionsbyCardnb(String cardNb) {
        logger.info("Fetching transactions for card number: {}", cardNb);
        Card card = cardRepository.findCardByCardNumber(cardNb);
        if (card == null) {
            logger.error("Card not found with number: {}", cardNb);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }

        List<TransactionResponse> transactions = transactionRepository.findByCard(card).stream()
                .map(transaction -> modelMapper.map(transaction, TransactionResponse.class))
                .toList();

        logger.info("Found {} transactions for card number: {}", transactions.size(), cardNb);
        return transactions;
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        logger.info("Fetching all transactions");
        List<TransactionResponse> transactions = transactionRepository.findAll().stream()
                .map(transaction -> modelMapper.map(transaction, TransactionResponse.class))
                .toList();
        logger.info("Fetched {} transactions", transactions.size());
        return transactions;
    }

    @Override
    public ResponseEntity<String> deleteTransaction(UUID id) {
        logger.info("Deleting transaction with ID: {}", id);
        if (!transactionRepository.existsById(id)) {
            logger.error("Transaction not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
        logger.info("Transaction with ID {} deleted successfully", id);
        return ResponseEntity.ok("Transaction deleted successfully");
    }

    @Override
    public ResponseEntity<String> updateTransaction(UUID id, TransactionRequest updateRequest) {
        logger.info("Updating transaction with ID: {}", id);

        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null) {
            logger.error("Transaction not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found with id: " + id);
        }

        Card card = cardRepository.findCardByCardNumber(updateRequest.getCardNb());
        if (card == null) {
            logger.error("Card not found with number: {}", updateRequest.getCardNb());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
        }

        if (card.getStatus() != StatusType.ACTIVE || card.getExpiryDate().isBefore(LocalDate.now())) {
            logger.error("Card not active or expired. Card ID: {}", card.getId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Card not active or expired");
        }

        transaction.setTransactionAmount(updateRequest.getTransactionAmount());
        transaction.setTransactionType(updateRequest.getTransactionType());
        transaction.setCard(card);
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);
        logger.info("Transaction with ID {} updated successfully", id);
        return ResponseEntity.ok("Transaction updated successfully");
    }
}
