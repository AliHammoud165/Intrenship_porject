package com.CMS.CMS.controllers;

import com.CMS.CMS.dtos.CardAccountRequest;
import com.CMS.CMS.models.CardAccount;
import com.CMS.CMS.services.implementation.CardAccountServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/card_account")
@RequiredArgsConstructor
@Tag(name = "CardAccount", description = "API for linking cards to accounts")
public class CardAccountController {

    private final CardAccountServiceImplementation cardAccountServiceImplementation;

    @Operation(summary = "Create a new CardAccount link", description = "Links a card to an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CardAccount created successfully"),
            @ApiResponse(responseCode = "404", description = "Card or Account not found"),
    })
    @PostMapping("/create")
    public ResponseEntity<String> createNewAccount(@RequestBody CardAccountRequest cardAccountRequest) {
        return cardAccountServiceImplementation.createCardAccount(cardAccountRequest);
    }

    @Operation(summary = "Get all CardAccount links by Card ID", description = "Returns all CardAccount mappings for a given card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CardAccount list retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card  not found")
    })
    @GetMapping("/get_by_card_id/{id}")
    public List<CardAccount> getByCardId(@PathVariable UUID id) {
        return cardAccountServiceImplementation.getCardAccountbycardid(id);
    }

    @GetMapping("/all")
    public List<CardAccount> getAllCardAccounts() {
        return cardAccountServiceImplementation.getAllCardAccounts();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCardAccount(@PathVariable UUID id) {
        return cardAccountServiceImplementation.deleteCardAccount(id);
    }

}
