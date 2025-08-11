package com.CMS.CMS.Repositories;

import com.CMS.CMS.Entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    @Query("SELECT c FROM Card c WHERE c.CardNumber = :cardNumber")
    Card findByCardNumber(@Param("cardNumber") String cardNumber);

    Card findCardById(UUID id);
}


