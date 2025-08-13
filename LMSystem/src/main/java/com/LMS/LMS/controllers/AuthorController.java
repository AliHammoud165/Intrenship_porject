package com.LMS.LMS.controllers;

import com.LMS.LMS.dtos.AuthorRequest;
import com.LMS.LMS.dtos.AuthorUpdateRequest;
import com.LMS.LMS.models.Author;
import com.LMS.LMS.services.implementation.AuthorServiceImplementation;
<<<<<<< HEAD
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
=======
>>>>>>> d599a845c61de5c926be53327c33faca6a6cf504
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
<<<<<<< HEAD
@Tag(name = "Author", description = "API for managing authors")
public class AuthorController {
    private final AuthorServiceImplementation authorServiceImplementation;

    @Operation(summary = "Create a new author", description = "Create an author if the name doesn't already exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "409", description = "Author already exists"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createAuthor(@RequestBody AuthorRequest authorRequest){
        return authorServiceImplementation.createAuthor(authorRequest);
    }

    @Operation(summary = "Update an existing author", description = "Update author fields by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/update")
    public ResponseEntity<String> updateAuthor(@RequestBody AuthorUpdateRequest author){
        return authorServiceImplementation.updateAuthor(author);
    }

    @Operation(summary = "Get an author by ID", description = "Retrieve author details by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author retrieved"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAuthorById(
            @Parameter(description = "UUID of the author", required = true)
            @PathVariable UUID id){
        return authorServiceImplementation.getAuthorById(id);
    }

    @Operation(summary = "Get all authors", description = "Return a list of all authors")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping("/get_all")
=======
public class AuthorController {
    private final AuthorServiceImplementation authorServiceImplementation;

    @PostMapping("/create")
    public ResponseEntity<String> createAuthor( @RequestBody AuthorRequest  authorRequest){
        return authorServiceImplementation.createAuthor(authorRequest);
    }
    @PutMapping ("/update")
    public ResponseEntity<String> updateAuthor( @RequestBody AuthorUpdateRequest author){
        return authorServiceImplementation.updateAuthor(author);
    }
    @GetMapping ("/get/{id}")
    public ResponseEntity<?> getAuthorById( @PathVariable UUID id){
        return authorServiceImplementation.getAuthorById(id);
    }
    @GetMapping ("/get_all")
>>>>>>> d599a845c61de5c926be53327c33faca6a6cf504
    public List<Author> getAllAuthors(){
        return authorServiceImplementation.getAllAuthors();
    }

<<<<<<< HEAD
    @Operation(summary = "Delete author by ID", description = "Delete an author by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthorById(
            @Parameter(description = "UUID of the author to delete", required = true)
            @PathVariable UUID id){
        return authorServiceImplementation.deleteAuthor(id);
    }
=======
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteAuthorById( @PathVariable UUID id){
        return authorServiceImplementation.deleteAuthor(id);
    }


>>>>>>> d599a845c61de5c926be53327c33faca6a6cf504
}
