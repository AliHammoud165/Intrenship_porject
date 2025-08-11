package com.LMS.LMS.Repositories;

import com.LMS.LMS.Entities.Book;
import com.LMS.LMS.Entities.Borrower;
import com.LMS.LMS.Entities.BorrowingTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransactions, UUID> {

    @Query("SELECT bt FROM BorrowingTransactions bt WHERE bt.borrower.Email = :email")
    List<BorrowingTransactions> findAllByBorrowerEmail(@Param("email") String email);

}
