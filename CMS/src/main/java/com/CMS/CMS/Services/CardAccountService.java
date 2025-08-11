package com.CMS.CMS.Services;

import com.CMS.CMS.DTOs.CardAccountRequest;
import com.CMS.CMS.Entities.Account;
import com.CMS.CMS.Entities.Card;
import com.CMS.CMS.Entities.CardAccount;
import com.CMS.CMS.Enums.StatusType;
import com.CMS.CMS.Repositories.AccountRepository;
import com.CMS.CMS.Repositories.CardAccountRepository;
import com.CMS.CMS.Repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CardAccountService {

  private final CardRepository cardRepository;
  private final AccountRepository accountRepository;
  private final CardAccountRepository cardAccountRepository;

  public CardAccount getCardAccountbyid(UUID id) {
    CardAccount cardAccount = cardAccountRepository.getCardAccountById(id);
    if (cardAccount == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CardAccount with ID " + id + " not found");
    }
    return cardAccount;
  }

  public List<CardAccount> getCardAccountbycardid(UUID id) {
    List<CardAccount> cardAccounts = cardAccountRepository.getReferenceByCardID(id);
    if (cardAccounts == null || cardAccounts.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No CardAccounts found for Card ID " + id);
    }
    return cardAccounts;
  }



  public ResponseEntity<String> createCardAccount(CardAccountRequest request) {
    try {
      Card card = cardRepository.findById(request.getCardID())
              .orElse(null);

      if (card == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
      }

      Account account = accountRepository.findById(request.getAccountID())
              .orElse(null);

      if (account == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
      }

      CardAccount cardAccount = CardAccount.builder()
              .card(card)
              .account(account)
              .build();

      cardAccountRepository.save(cardAccount);
      return ResponseEntity.status(HttpStatus.CREATED).body("CardAccount created successfully");

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Failed to create CardAccount: " + e.getMessage());
    }
  }




}
