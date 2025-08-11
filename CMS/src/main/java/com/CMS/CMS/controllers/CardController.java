package com.CMS.CMS.controllers;

import com.CMS.CMS.DTOs.CardResponse;
import com.CMS.CMS.Services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
@Tag(name = "Card", description = "API for managing cards")
public class CardController {

    private final CardService cardService;

    @Operation(summary = "Create a new card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card created successfully")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createCard() {
        cardService.CreateNewCard();
        return ResponseEntity.status(201).body("Card created successfully");
    }

    @Operation(summary = "Get card by card number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @GetMapping("/getcardbynumber")
    public CardResponse getCard(@RequestParam String cardNumber) {
        return cardService.GetCardbynumber(cardNumber);
    }

    @Operation(summary = "Get card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @GetMapping("/getcardbyid")
    public CardResponse getCard(@RequestParam UUID id) {
        return cardService.GetCardbyid(id);
    }

    @Operation(summary = "Switch card status (ACTIVE/INACTIVE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card status switched successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @PatchMapping ("/swichcardstatus")
    public ResponseEntity<String> swichStatus(@RequestParam UUID id) {
        return cardService.switchStatus(id);
    }
}
