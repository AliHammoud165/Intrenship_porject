package com.LMS.LMS.Repositories;

import com.LMS.LMS.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Author getAuthorById(UUID id);
    @Query("SELECT a FROM Author a")
    List<Author> getAllAuthors();

    @Query("SELECT a FROM Author a WHERE LOWER(a.Name) = LOWER(:name)")
    Optional<Author> findByNameIgnoreCase(@Param("name") String name);
}
