package com.LMS.LMS.services.implementation;

import com.LMS.LMS.dtos.AuthorRequest;
import com.LMS.LMS.dtos.AuthorUpdateRequest;
import com.LMS.LMS.models.Author;
import com.LMS.LMS.repositories.AuthorRepository;
import com.LMS.LMS.services.inter.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthorServiceImplementation implements AuthorService {
    private  final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImplementation.class);



    @Override
public ResponseEntity<String> createAuthor(AuthorRequest authorRequest){

        logger.info("Creating Author :{}", authorRequest.toString());

        Optional<Author> checkauthor = authorRepository.findByNameIgnoreCase(authorRequest.getName());
        if(checkauthor.isPresent()){
            logger.error("Author with name '{}' already exists", authorRequest.getName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Author already exists");
        }

        Author author = modelMapper.map(authorRequest, Author.class);

        if (author.getName()==null){
            logger.error("Author name is null");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Author Name or Biography is null");

        }
        authorRepository.save(author);
        logger.info("Author '{}' created successfully", author.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Author created successfully");
    }

@Override
public ResponseEntity<String> updateAuthor(AuthorUpdateRequest authorUpdateRequest) {
    logger.info("Updating Author :{}", authorUpdateRequest.toString());

    Author author = authorRepository.findById(authorUpdateRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
    logger.error("Author with id '{}'  Not found)", authorUpdateRequest.getId().toString());


    modelMapper.map(authorUpdateRequest, author);
        authorRepository.save(author);
    logger.info("Author with id  '{}' created successfully", author.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Author updated successfully");
    }
    @Override
    public ResponseEntity<?> getAuthorById(UUID id) {
        logger.info("Getting  Author with id  :{}", id);

        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isEmpty()) {
            logger.error(" Author with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Author not found");

        }
        logger.info(" Author with id {} found successfully", id);
        return ResponseEntity.ok(authorOptional.get());
    }
@Override
public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public ResponseEntity<String> deleteAuthor(UUID id) {
        logger.info("Attempting to delete author with id: {}", id);

        if (!authorRepository.existsById(id)) {
            logger.warn("Author with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        }

        authorRepository.deleteById(id);
        logger.info("Author with id {} deleted successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body("Author deleted successfully");
    }
}
