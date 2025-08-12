package com.CMS.CMS.repositories;

import com.CMS.CMS.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account getAccountById(UUID id);
}
