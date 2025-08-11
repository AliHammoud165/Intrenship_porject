package com.LMS.LMS.Services;

import com.LMS.LMS.DTOs.AuthorRequest;
import com.LMS.LMS.Entities.Author;
import com.LMS.LMS.Repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private  final AuthorRepository authorRepository;

    public ResponseEntity<String> createAuthor (AuthorRequest authorRequest){

        Optional<Author> checkauthor = authorRepository.findByNameIgnoreCase(authorRequest.getName());
        if(checkauthor.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Author already exists");
        }

        Author author = Author.builder()
                .Name( authorRequest.getName())
                .build();
        if (author.getName()==null){

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Author Name or Biography is null");

        }
        authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body("Author created successfully");
    }

    public ResponseEntity<String> updateAuthor (Author request){
        Author author=authorRepository.getReferenceById(request.getId());
        author.setName(request.getName());
        author.setBiography(request.getBiography());
        authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.OK).body("Author updated successfully");
    }
    public ResponseEntity<?> getAuthorById(UUID id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Author not found");
        }

        return ResponseEntity.ok(authorOptional.get());
    }

    public List<Author> getAllAuthors(){
        return authorRepository.getAllAuthors();
    }

    public ResponseEntity<String> deleteAuthor(UUID id) {
        if (!authorRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        }
        authorRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Author deleted successfully");
    }
}
