package com.CMS.CMS.Services;

import com.CMS.CMS.DTOs.AccountRequest;
import com.CMS.CMS.DTOs.AccountResponse;
import com.CMS.CMS.DTOs.BalanceUpdateRequest;
import com.CMS.CMS.Entities.Account;
import com.CMS.CMS.Enums.StatusType;
import com.CMS.CMS.Mappers.AccountMapper;
import com.CMS.CMS.Repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public ResponseEntity<String> CreateNewAccount(AccountRequest accountRequest) {
        Account account = Account.builder()
                .Status(StatusType.ACTIVE)
                .Currency(accountRequest.getCurrency())
                .Balance(accountRequest.getBalance())
                .build();

        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
    }

    public ResponseEntity<String> deleteAccount(UUID id) {
        Account account = accountRepository.getAccountById(id);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Account deleted successfully");
    }

    public AccountResponse getAccountbyid(UUID id) {
        Account account = accountRepository.getAccountById(id);
        if (account == null) {
            throw new IllegalArgumentException("Account not found with id: " + id);
        }
        return AccountMapper.mapToAccountResponse(account);
    }

    public ResponseEntity<String> updatebalance(BalanceUpdateRequest balanceUpdateRequest) {
        Account account = accountRepository.getAccountById(balanceUpdateRequest.getId());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with id: " + balanceUpdateRequest.getId());
        }
        account.setBalance(balanceUpdateRequest.getBalance());
        accountRepository.save(account);
        return ResponseEntity.ok("Account balance updated successfully");
    }

    public ResponseEntity<String> switchStatus(UUID id) {
        Account account = accountRepository.getAccountById(id);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with id: " + id);
        }

        if (account.getStatus() == StatusType.ACTIVE) {
            account.setStatus(StatusType.INACTIVE);
        } else if (account.getStatus() == StatusType.INACTIVE) {
            account.setStatus(StatusType.ACTIVE);
        }

        accountRepository.save(account);
        return ResponseEntity.ok("Account status changed successfully");
    }
    public ResponseEntity<String> updateAllAccountFields(Account request) {
        Account account = accountRepository.getAccountById(request.getId());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with id: " + request.getId());
        }

        account.setStatus(request.getStatus());
        account.setCurrency(request.getCurrency());
        account.setBalance(request.getBalance());

        accountRepository.save(account);
        return ResponseEntity.ok("Account updated successfully");
    }
}
