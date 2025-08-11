package com.CMS.CMS.Repositories;

import com.CMS.CMS.Entities.CardAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CardAccountRepository extends JpaRepository<CardAccount, UUID> {
     CardAccount getCardAccountById(UUID id);


    @Query("SELECT ca FROM CardAccount ca WHERE ca.card.Id = :uuid")
    List<CardAccount> getReferenceByCardID(@Param("uuid") UUID uuid);}
