package com.CMS.CMS.Services;

import com.CMS.CMS.DTOs.CardResponse;
import com.CMS.CMS.Entities.Card;
import com.CMS.CMS.Enums.StatusType;
import com.CMS.CMS.Mappers.CardMapper;
import com.CMS.CMS.Repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private static final SecureRandom random = new SecureRandom();

    public void CreateNewCard ( ) {
        Card card = Card.builder()
                .Status(StatusType.ACTIVE)
                .ExpiryDate(LocalDate.now().plusYears(1))
                .CardNumber(generateCardNumber())
                .build();

        cardRepository.save(card);

    }
    public CardResponse GetCardbynumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
        return CardMapper.mapToCardResponse(card);
    }

    public CardResponse GetCardbyid(UUID id) {
        Card card = cardRepository.findCardById(id);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
        return CardMapper.mapToCardResponse(card);
    }

    private String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10)); // digits 0-9
        }
        return sb.toString();
    }

    public ResponseEntity<String> switchStatus(UUID Id) {
        Card card = cardRepository.findCardById(Id);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
        if (card.getStatus() == StatusType.ACTIVE){
        card.setStatus(StatusType.INACTIVE);}
        else if (card.getStatus() == StatusType.INACTIVE){
            card.setStatus(StatusType.ACTIVE);
        }
        cardRepository.save(card);
        throw new ResponseStatusException(HttpStatus.OK, "Card status changed successfully");

    }
}
