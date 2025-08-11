package com.CMS.CMS.Mappers;

import com.CMS.CMS.DTOs.AccountResponse;
import com.CMS.CMS.Entities.Account;

public final class AccountMapper {

    public static AccountResponse mapToAccountResponse(Account account) {
        return new AccountResponse(
                account.getStatus(),
                account.getCurrency(),
                account.getBalance()
        );
    }
}
