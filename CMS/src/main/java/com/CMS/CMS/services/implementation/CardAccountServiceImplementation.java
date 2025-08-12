package com.CMS.CMS.services.implementation;

import com.CMS.CMS.dtos.CardAccountRequest;
import com.CMS.CMS.models.Account;
import com.CMS.CMS.models.Card;
import com.CMS.CMS.models.CardAccount;
import com.CMS.CMS.repositories.AccountRepository;
import com.CMS.CMS.repositories.CardAccountRepository;
import com.CMS.CMS.repositories.CardRepository;
import com.CMS.CMS.services.inter.CardAccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CardAccountServiceImplementation implements CardAccountService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final CardAccountRepository cardAccountRepository;
    private static final Logger logger = LoggerFactory.getLogger(CardAccountServiceImplementation.class);

    @Override
    public List<CardAccount> getCardAccountbycardid(UUID id) {
        logger.info("Fetching CardAccounts for Card ID: {}", id);

        List<CardAccount> cardAccounts = cardAccountRepository.getCardAccountByCard_Id(id);
        if (cardAccounts == null || cardAccounts.isEmpty()) {
            logger.error("No CardAccounts found for Card ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No CardAccounts found for Card ID " + id);
        }

        logger.info("Found {} CardAccounts for Card ID: {}", cardAccounts.size(), id);
        return cardAccounts;
    }

    @Override
    public ResponseEntity<String> createCardAccount(CardAccountRequest request) {
        logger.info("Creating CardAccount for Card ID: {} and Account ID: {}", request.getCardID(), request.getAccountID());

        try {
            Card card = cardRepository.findById(request.getCardID()).orElse(null);
            if (card == null) {
                logger.error("Card not found with ID: {}", request.getCardID());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
            }

            Account account = accountRepository.findById(request.getAccountID()).orElse(null);
            if (account == null) {
                logger.error("Account not found with ID: {}", request.getAccountID());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
            }

            CardAccount cardAccount = CardAccount.builder()
                    .card(card)
                    .account(account)
                    .build();

            cardAccountRepository.save(cardAccount);
            logger.info("CardAccount created successfully with ID: {}", cardAccount.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body("CardAccount created successfully");
        } catch (Exception e) {
            logger.error("Failed to create CardAccount: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create CardAccount: " + e.getMessage());
        }
    }

    @Override
    public List<CardAccount> getAllCardAccounts() {
        logger.info("Fetching all CardAccounts");

        List<CardAccount> cardAccounts = cardAccountRepository.findAll();
        logger.info("Fetched {} CardAccounts", cardAccounts.size());

        return cardAccounts;
    }

    @Override
    public ResponseEntity<String> deleteCardAccount(UUID id) {
        logger.info("Deleting CardAccount with ID: {}", id);

        if (!cardAccountRepository.existsById(id)) {
            logger.error("CardAccount not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CardAccount not found with id: " + id);
        }

        cardAccountRepository.deleteById(id);
        logger.info("CardAccount with ID {} deleted successfully", id);

        return ResponseEntity.status(HttpStatus.OK).body("CardAccount deleted successfully");
    }
}
