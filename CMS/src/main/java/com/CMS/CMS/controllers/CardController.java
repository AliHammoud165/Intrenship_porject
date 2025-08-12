package com.CMS.CMS.controllers;

import com.CMS.CMS.dtos.CardResponse;
import com.CMS.CMS.services.implementation.CardServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    private final CardServiceImplementation cardService;

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

    @Operation(summary = "Get all cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards retrieved successfully")
    })
    @GetMapping("/all")
    public List<CardResponse> getAllCards() {
        return cardService.getAllCards();
    }

    @Operation(summary = "Delete card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCard(@RequestParam UUID id) {
        return cardService.deleteCard(id);
    }

    @Operation(summary = "Switch card status (ACTIVE/INACTIVE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card status switched successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @PatchMapping("/switchcardstatus")
    public ResponseEntity<String> switchStatus(@RequestParam UUID id) {
        return cardService.switchStatus(id);
    }
}
