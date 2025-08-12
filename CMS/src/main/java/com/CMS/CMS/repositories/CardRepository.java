package com.CMS.CMS.repositories;

import com.CMS.CMS.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    Card findCardByCardNumber(String cardNumber);
    Card findCardById(UUID id);
}


