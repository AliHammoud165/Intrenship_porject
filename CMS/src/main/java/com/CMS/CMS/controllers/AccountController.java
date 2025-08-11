package com.CMS.CMS.controllers;

import com.CMS.CMS.DTOs.AccountRequest;
import com.CMS.CMS.DTOs.AccountResponse;
import com.CMS.CMS.DTOs.BalanceUpdateRequest;
import com.CMS.CMS.Entities.Account;
import com.CMS.CMS.Services.AccountService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Tag(name = "Account", description = "API for managing accounts")
public class AccountController {
    private final   AccountService accountService;

    @Operation(summary = "Create a new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountRequest accountRequest) {
       return accountService.CreateNewAccount(accountRequest);
    }
    @Operation(summary = "Delete an account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found with id")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestParam  UUID id) {
       return accountService.deleteAccount(id);
    }
    @Operation(summary = "Get account by ID", description = "Returns account details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account get successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found with id")
    })
    @GetMapping("/get")
    public AccountResponse getAccount(@RequestParam  UUID id) {
return accountService.getAccountbyid(id) ;
    }

    @Operation(summary = "Switch account Status", description = "Returns account details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account Status changed successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found with id")
    })
    @PatchMapping ("/swichaccountstatus")
    public ResponseEntity<String> swichStatus(@RequestParam UUID id) {
       return accountService.switchStatus(id);
    }
    @Operation(summary = "Update account balance", description = "Returns account details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account  balance updated successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found with id")
    })
    @PatchMapping ("/updatebalance")
    public ResponseEntity<String> updateBalance(@RequestBody BalanceUpdateRequest balanceUpdateRequest) {
       return accountService.updatebalance(balanceUpdateRequest);
    }
    @Operation(summary = "Update all account fields", description = "Returns account details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account   updated successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found with id")
    })
    @PutMapping ("/updateall")
    public ResponseEntity<String> updateAllFields(@RequestBody Account request) {
        return accountService.updateAllAccountFields(request);
    }


}
