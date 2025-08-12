package com.CMS.CMS.services.inter;

import com.CMS.CMS.dtos.CardAccountRequest;
import com.CMS.CMS.models.CardAccount;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CardAccountService {
    List<CardAccount> getCardAccountbycardid(UUID id);

    ResponseEntity<String> createCardAccount(CardAccountRequest request);

    List<CardAccount> getAllCardAccounts();

    ResponseEntity<String> deleteCardAccount(UUID id);
}
