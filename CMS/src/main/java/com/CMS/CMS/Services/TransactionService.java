package com.CMS.CMS.Services;

import com.CMS.CMS.DTOs.AccountResponse;
import com.CMS.CMS.DTOs.BalanceUpdateRequest;
import com.CMS.CMS.DTOs.TransactionRequest;
import com.CMS.CMS.DTOs.TransactionRespond;
import com.CMS.CMS.Entities.Account;
import com.CMS.CMS.Entities.Card;
import com.CMS.CMS.Entities.CardAccount;
import com.CMS.CMS.Entities.Transaction;
import com.CMS.CMS.Enums.CurrencyType;
import com.CMS.CMS.Enums.StatusType;
import com.CMS.CMS.Enums.TransactionType;
import com.CMS.CMS.Repositories.AccountRepository;
import com.CMS.CMS.Repositories.CardAccountRepository;
import com.CMS.CMS.Repositories.CardRepository;
import com.CMS.CMS.Repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final ModelMapper modelMapper;

    private final TransactionRepository transactionRepository;
    private  final  AccountService accountService;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CardService cardService;
    private final CardAccountService cardAccountService;
    private final CardAccountRepository cardAccountRepository;

    public ResponseEntity<String> CreateTransaction(TransactionRequest transactionRequest) {
      Card card=  cardRepository.findByCardNumber(transactionRequest.getCardnb());
        if (validate(transactionRequest)) {
            Transaction transaction = Transaction.builder()
                    .card(cardRepository.findByCardNumber(transactionRequest.getCardnb()))
                    .TransactionAmount(transactionRequest.getTransactionAmount())
                    .TransactionType(transactionRequest.getTransactionType())
                    .Currency(transactionRequest.getCurrencyType())
                    .TransactionDate(LocalDateTime.now())
                    .build();
            transactionRepository.save(transaction);

            return ResponseEntity.status(201).body("Transaction successfully");
        }
        else {
            return ResponseEntity.badRequest().body("Invalid Transaction");
        }
    }


    private boolean validate(TransactionRequest transaction) {

       Card card=cardRepository.findByCardNumber(transaction.getCardnb());

        List<CardAccount> cardAccounts=cardAccountRepository.getReferenceByCardID(card.getId());

        List<CardAccount> filterAccounts = cardAccounts.stream()
                .filter(ca -> ca.getAccount().getCurrency() ==transaction.getCurrencyType())
                .toList();

        System.out.println(filterAccounts);

        AccountResponse accountResponse = accountService.getAccountbyid(filterAccounts.get(0).getAccount().getId());

        Card card2 = cardRepository.findByCardNumber(transaction.getCardnb());

        if (card.getStatus() != StatusType.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Card not active");
        }


        if (card.getExpiryDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Card expired");
        }

        if (accountResponse.getStatus() != StatusType.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account not active");
        }

        if (accountResponse.getBalance().compareTo(transaction.getTransactionAmount()) < 0&& transaction.getTransactionType()== TransactionType.D) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dont have enough balance");
        }
            if(transaction.getTransactionType()== TransactionType.D){
            BigDecimal newBalance = accountResponse.getBalance().subtract(transaction.getTransactionAmount());
            BalanceUpdateRequest balanceUpdateRequest = new BalanceUpdateRequest(filterAccounts.get(0).getAccount().getId(), newBalance);
            accountService.updatebalance(balanceUpdateRequest);
            }
            else if(transaction.getTransactionType()== TransactionType.C){
                BigDecimal newBalance = accountResponse.getBalance().add(transaction.getTransactionAmount());
                BalanceUpdateRequest balanceUpdateRequest = new BalanceUpdateRequest(filterAccounts.get(0).getAccount().getId(), newBalance);
                accountService.updatebalance(balanceUpdateRequest);
            }

            return true;


    }
    public boolean isAccountLinkedToCard(UUID accountId, UUID cardId) {
        List<CardAccount> cardAccounts = cardAccountService.getCardAccountbycardid(cardId);

        for (CardAccount cardAccount : cardAccounts) {
            if (cardAccount.getAccount().getId().equals(accountId)) {
                return true;
            }
        }

        return false;
    }

    public List<TransactionRespond> getTransactionsbyCardnb(String cardNb) {
        Card card =cardRepository.findByCardNumber(cardNb);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }

        return transactionRepository.findByCard(card).stream()
                .map(transaction -> modelMapper.map(transaction, TransactionRespond.class))
                .toList();
    }

}
