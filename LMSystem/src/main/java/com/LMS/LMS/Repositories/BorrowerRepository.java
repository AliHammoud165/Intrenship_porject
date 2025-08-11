package com.LMS.LMS.Repositories;

import com.LMS.LMS.Entities.Book;
import com.LMS.LMS.Entities.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {
    Borrower getById(UUID id);
    @Query("SELECT b FROM Borrower b ")
    List<Borrower> getAllBorrowers();
    @Query("SELECT b FROM Borrower b WHERE b.Email = :email")
    Optional<Borrower> findByEmail(String email);

}
