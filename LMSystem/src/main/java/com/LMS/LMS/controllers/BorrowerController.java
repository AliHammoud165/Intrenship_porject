package com.LMS.LMS.controllers;

import com.LMS.LMS.dtos.BorrowerRequest;
import com.LMS.LMS.dtos.BorrowerUpdateRequest;
import com.LMS.LMS.models.Borrower;
import com.LMS.LMS.services.implementation.BorrowerServiceImplementation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/borrower")
@RequiredArgsConstructor
@Tag(name = "Borrower", description = "API for managing borrowers")
public class BorrowerController {

    private final BorrowerServiceImplementation borrowerServiceImplementation;

    @Operation(summary = "Create a new borrower", description = "Create a borrower with required details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Borrower created successfully"),
            @ApiResponse(responseCode = "403", description = "Missing required fields or invalid email format"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createBorrower(
            @Parameter(description = "Borrower creation request", required = true)
            @RequestBody BorrowerRequest borrowerRequest) {
        return borrowerServiceImplementation.createBorrower(borrowerRequest);
    }

    @Operation(summary = "Update an existing borrower", description = "Update borrower details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Borrower updated successfully"),
            @ApiResponse(responseCode = "404", description = "Borrower not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/update")
    public ResponseEntity<String> updateBorrower(
            @Parameter(description = "Borrower update request with ID and updated fields", required = true)
            @RequestBody BorrowerUpdateRequest borrowerUpdateRequest) {
        return borrowerServiceImplementation.updateBorrower(borrowerUpdateRequest);
    }

    @Operation(summary = "Get a borrower by ID", description = "Retrieve borrower details by UUID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Borrower retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Borrower.class))
            ),
            @ApiResponse(responseCode = "404", description = "Borrower not found")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBorrowerById(
            @Parameter(description = "UUID of the borrower", required = true)
            @PathVariable UUID id) {
        return borrowerServiceImplementation.getBorrowerId(id);
    }

    @Operation(summary = "Get all borrowers", description = "Retrieve all borrowers")
    @ApiResponse(
            responseCode = "200",
            description = "List of borrowers returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Borrower.class))
    )
    @GetMapping("/get_all")
    public List<Borrower> getAllBorrowers() {
        return borrowerServiceImplementation.getAllBorrowers();
    }

    @Operation(summary = "Delete a borrower by ID", description = "Delete a borrower by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Borrower deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Borrower not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBorrowerById(
            @Parameter(description = "UUID of the borrower to delete", required = true)
            @PathVariable UUID id) {
        return borrowerServiceImplementation.deleteBorrower(id);
    }
}
