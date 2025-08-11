package com.CMS.CMS.Mappers;

import com.CMS.CMS.DTOs.CardResponse;
import com.CMS.CMS.Entities.Card;

public class CardMapper {
    public static CardResponse mapToCardResponse(Card card) {
        return new CardResponse(
                card.getCardNumber(),
                card.getExpiryDate(),
                card.getStatus()
        );
    }
}
