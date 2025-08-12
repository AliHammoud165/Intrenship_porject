package com.LMS.LMS.repositories;

import com.LMS.LMS.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) = LOWER(:name)")
    Optional<Author> findByNameIgnoreCase(@Param("name") String name);
}
