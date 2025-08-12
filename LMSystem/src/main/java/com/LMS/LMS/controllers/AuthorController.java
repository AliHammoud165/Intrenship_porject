package com.LMS.LMS.controllers;

import com.LMS.LMS.dtos.AuthorRequest;
import com.LMS.LMS.dtos.AuthorUpdateRequest;
import com.LMS.LMS.models.Author;
import com.LMS.LMS.services.implementation.AuthorServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
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
    public List<Author> getAllAuthors(){
        return authorServiceImplementation.getAllAuthors();
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteAuthorById( @PathVariable UUID id){
        return authorServiceImplementation.deleteAuthor(id);
    }


}
