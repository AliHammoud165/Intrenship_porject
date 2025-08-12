package com.LMS.LMS.services.inter;

import com.LMS.LMS.dtos.BorrowerRequest;
import com.LMS.LMS.dtos.BorrowerUpdateRequest;
import com.LMS.LMS.models.Borrower;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface BorrowerService {
    ResponseEntity<String> createBorrower (BorrowerRequest borrowerRequest);

    ResponseEntity<String> updateBorrower(BorrowerUpdateRequest borrowerUpdateRequest);

    ResponseEntity<?> getBorrowerId(UUID id);

    List<Borrower> getAllBorrowers();

    ResponseEntity<String> deleteBorrower(UUID id);
}
