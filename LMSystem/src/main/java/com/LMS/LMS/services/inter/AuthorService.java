package com.LMS.LMS.services.inter;

import com.LMS.LMS.dtos.AuthorRequest;
import com.LMS.LMS.dtos.AuthorUpdateRequest;
import com.LMS.LMS.models.Author;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AuthorService {
    ResponseEntity<String> createAuthor (AuthorRequest authorRequest);

    ResponseEntity<String> updateAuthor(AuthorUpdateRequest authorUpdateRequest);

    ResponseEntity<?> getAuthorById(UUID id);

    List<Author> getAllAuthors();

    ResponseEntity<String> deleteAuthor(UUID id);
}
