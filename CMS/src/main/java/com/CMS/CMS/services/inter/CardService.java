package com.CMS.CMS.services.inter;

import com.CMS.CMS.dtos.CardResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CardService {
    void CreateNewCard();

    CardResponse GetCardbynumber(String cardNumber);

    CardResponse GetCardbyid(UUID id);

    String generateCardNumber();

    ResponseEntity<String> switchStatus(UUID Id);

    List<CardResponse> getAllCards();

    ResponseEntity<String> deleteCard(UUID id);
}
