package com.LMS.LMS.controllers;

import com.LMS.LMS.dtos.BorrowerRequest;
import com.LMS.LMS.dtos.BorrowerUpdateRequest;
import com.LMS.LMS.models.Borrower;
import com.LMS.LMS.services.implementation.BorrowerServiceImplementation;
import com.LMS.LMS.services.implementation.BorrowingTransactionServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/borrower")
@RequiredArgsConstructor
public class BorrowerController {
    private final BorrowerServiceImplementation borrowerServiceImplementation;

    @PostMapping("/create")
    public ResponseEntity<String> createBorrower(@RequestBody BorrowerRequest borrowerRequest){
        return borrowerServiceImplementation.createBorrower(borrowerRequest);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateBorrower( @RequestBody BorrowerUpdateRequest borrower){
        return borrowerServiceImplementation.updateBorrower(borrower);
    }
    @GetMapping ("/get/{id}")
    public ResponseEntity<?> getBorrowerById( @PathVariable UUID id){
        return borrowerServiceImplementation.getBorrowerId(id);
    }
    @GetMapping ("/get_all")
    public List<Borrower> getAllBorrower(){
        return borrowerServiceImplementation.getAllBorrowers();
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteBorrowerById( @PathVariable UUID id){
        return borrowerServiceImplementation.deleteBorrower(id);
    }

}
