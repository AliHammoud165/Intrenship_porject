package com.CMS.CMS.services.implementation;

import com.CMS.CMS.dtos.AccountRequest;
import com.CMS.CMS.dtos.AccountResponse;
import com.CMS.CMS.dtos.BalanceUpdateRequest;
import com.CMS.CMS.models.Account;
import com.CMS.CMS.enums.StatusType;
import com.CMS.CMS.repositories.AccountRepository;
import com.CMS.CMS.services.inter.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImplementation implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImplementation.class);

    @Override
    public ResponseEntity<String> CreateNewAccount(AccountRequest accountRequest) {
        logger.info("Creating new account with details: {}", accountRequest);

        Account account = modelMapper.map(accountRequest, Account.class);
        account.setStatus(StatusType.ACTIVE);

        accountRepository.save(account);
        logger.info("Account created successfully with ID: {}", account.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
    }

    @Override
    public ResponseEntity<String> deleteAccount(UUID id) {
        logger.info("Deleting account with ID: {}", id);

        Account account = accountRepository.getAccountById(id);
        if (account == null) {
            logger.error("Account not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with id: " + id);
        }

        accountRepository.deleteById(id);
        logger.info("Account with ID {} deleted successfully", id);

        return ResponseEntity.status(HttpStatus.OK).body("Account deleted successfully");
    }

    @Override
    public AccountResponse getAccountbyid(UUID id) {
        logger.info("Fetching account details for ID: {}", id);

        Account account = accountRepository.getAccountById(id);
        if (account == null) {
            logger.error("Account not found with ID: {}", id);
            throw new IllegalArgumentException("Account not found with id: " + id);
        }

        logger.info("Account details fetched successfully for ID: {}", id);
        return modelMapper.map(account, AccountResponse.class);
    }

    @Override
    public ResponseEntity<String> updatebalance(BalanceUpdateRequest balanceUpdateRequest) {
        logger.info("Updating balance for account ID: {}", balanceUpdateRequest.getId());

        Account account = accountRepository.getAccountById(balanceUpdateRequest.getId());
        if (account == null) {
            logger.error("Account not found with ID: {}", balanceUpdateRequest.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with id: " + balanceUpdateRequest.getId());
        }

        account.setBalance(balanceUpdateRequest.getBalance());
        accountRepository.save(account);

        logger.info("Balance updated successfully for account ID: {}", balanceUpdateRequest.getId());
        return ResponseEntity.ok("Account balance updated successfully");
    }

    @Override
    public ResponseEntity<String> switchStatus(UUID id) {
        logger.info("Switching status for account ID: {}", id);

        Account account = accountRepository.getAccountById(id);
        if (account == null) {
            logger.error("Account not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with id: " + id);
        }

        if (account.getStatus() == StatusType.ACTIVE) {
            account.setStatus(StatusType.INACTIVE);
            logger.info("Account ID {} status changed to INACTIVE", id);
        } else if (account.getStatus() == StatusType.INACTIVE) {
            account.setStatus(StatusType.ACTIVE);
            logger.info("Account ID {} status changed to ACTIVE", id);
        }

        accountRepository.save(account);
        return ResponseEntity.ok("Account status changed successfully");
    }

    @Override
    public ResponseEntity<String> updateAllAccountFields(Account request) {
        logger.info("Updating all fields for account ID: {}", request.getId());

        Account account = accountRepository.getAccountById(request.getId());
        if (account == null) {
            logger.error("Account not found with ID: {}", request.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with id: " + request.getId());
        }

        account.setStatus(request.getStatus());
        account.setCurrency(request.getCurrency());
        account.setBalance(request.getBalance());

        accountRepository.save(account);
        logger.info("Account ID {} updated successfully", request.getId());

        return ResponseEntity.ok("Account updated successfully");
    }

    @Override
    public List<Account> getAllAccounts() {
        logger.info("Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        logger.info("Fetched {} accounts", accounts.size());
        return accounts;
    }
}
