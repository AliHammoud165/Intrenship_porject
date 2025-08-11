package com.LMS.LMS.Controllers;

import com.LMS.LMS.DTOs.AuthorRequest;
import com.LMS.LMS.Entities.Author;
import com.LMS.LMS.Services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService ;

    @PostMapping("/create")
    public ResponseEntity<String> createAuthor( @RequestBody AuthorRequest  authorRequest){
        return authorService.createAuthor(authorRequest);
    }
    @PutMapping ("/update")
    public ResponseEntity<String> updateAuthor( @RequestBody Author author){
        return authorService.updateAuthor(author);
    }
    @GetMapping ("/get")
    public ResponseEntity<?> getAuthorById( @RequestParam UUID id){
        return authorService.getAuthorById(id);
    }
    @GetMapping ("/get_all")
    public List<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }
    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteAuthorById( @RequestParam UUID id){
        return authorService.deleteAuthor(id);
    }


}
