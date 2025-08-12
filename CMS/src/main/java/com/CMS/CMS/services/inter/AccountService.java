package com.CMS.CMS.services.inter;

import com.CMS.CMS.dtos.AccountRequest;
import com.CMS.CMS.dtos.AccountResponse;
import com.CMS.CMS.dtos.BalanceUpdateRequest;
import com.CMS.CMS.models.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService {
      ResponseEntity<String> CreateNewAccount(AccountRequest accountRequest) ;
    ResponseEntity<String> deleteAccount(UUID id);
    AccountResponse getAccountbyid(UUID id);
    ResponseEntity<String> updatebalance(BalanceUpdateRequest balanceUpdateRequest);

    ResponseEntity<String> switchStatus(UUID id);

    ResponseEntity<String> updateAllAccountFields(Account request);

    List<Account> getAllAccounts();
}
