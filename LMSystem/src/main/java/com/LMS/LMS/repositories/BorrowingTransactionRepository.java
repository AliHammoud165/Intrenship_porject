package com.LMS.LMS.repositories;

import com.LMS.LMS.models.BorrowingTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransactions, UUID> {

    List<BorrowingTransactions> findAllByBorrowerEmail(@Param("email") String email);

    List<BorrowingTransactions> findAllByBookId(UUID bookId);

    List<BorrowingTransactions> findAllByBorrowerId(UUID borrowerId);
}
