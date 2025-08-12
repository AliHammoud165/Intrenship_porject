package com.CMS.CMS.services.implementation;

import com.CMS.CMS.dtos.CardResponse;
import com.CMS.CMS.models.Card;
import com.CMS.CMS.enums.StatusType;
import com.CMS.CMS.repositories.CardRepository;
import com.CMS.CMS.services.inter.CardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImplementation implements CardService {

    private final CardRepository cardRepository;
    private static final SecureRandom random = new SecureRandom();
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImplementation.class);

    @Override
    public void CreateNewCard() {
        logger.info("Creating new Card");

        Card card = Card.builder()
                .status(StatusType.ACTIVE)
                .expiryDate(LocalDate.now().plusYears(1))
                .cardNumber(generateCardNumber())
                .build();

        cardRepository.save(card);

        logger.info("Card created successfully with Number: {} and Expiry Date: {}",
                card.getCardNumber(), card.getExpiryDate());
    }

    @Override
    public CardResponse GetCardbynumber(String cardNumber) {
        logger.info("Fetching Card by Number: {}", cardNumber);

        Card card = cardRepository.findCardByCardNumber(cardNumber);
        if (card == null) {
            logger.error("Card not found with Number: {}", cardNumber);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }

        logger.info("Card found with ID: {}", card.getId());
        return modelMapper.map(card, CardResponse.class);
    }

    @Override
    public CardResponse GetCardbyid(UUID id) {
        logger.info("Fetching Card by ID: {}", id);

        Card card = cardRepository.findCardById(id);
        if (card == null) {
            logger.error("Card not found with ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }

        logger.info("Card found with Number: {}", card.getCardNumber());
        return modelMapper.map(card, CardResponse.class);
    }

    @Override
    public String generateCardNumber() {
        logger.debug("Generating 16-digit card number");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10)); // digits 0-9
        }

        String cardNumber = sb.toString();
        logger.debug("Generated Card Number: {}", cardNumber);
        return cardNumber;
    }

    @Override
    public ResponseEntity<String> switchStatus(UUID id) {
        logger.info("Switching status for Card ID: {}", id);

        Card card = cardRepository.findCardById(id);
        if (card == null) {
            logger.error("Card not found with ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }

        if (card.getStatus() == StatusType.ACTIVE) {
            card.setStatus(StatusType.INACTIVE);
        } else if (card.getStatus() == StatusType.INACTIVE) {
            card.setStatus(StatusType.ACTIVE);
        }

        cardRepository.save(card);
        logger.info("Card status changed to: {}", card.getStatus());

        return ResponseEntity.ok("Card status changed successfully");
    }

    @Override
    public List<CardResponse> getAllCards() {
        logger.info("Fetching all Cards");

        List<Card> cards = cardRepository.findAll();
        logger.info("Fetched {} cards", cards.size());

        return cards.stream()
                .map(card -> modelMapper.map(card, CardResponse.class))
                .toList();
    }

    @Override
    public ResponseEntity<String> deleteCard(UUID id) {
        logger.info("Deleting Card with ID: {}", id);

        Card card = cardRepository.findCardById(id);
        if (card == null) {
            logger.error("Card not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found with id: " + id);
        }

        cardRepository.deleteById(id);
        logger.info("Card with ID {} deleted successfully", id);

        return ResponseEntity.ok("Card deleted successfully");
    }
}
