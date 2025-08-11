package com.LMS.LMS.Controllers;

import com.LMS.LMS.DTOs.AuthorRequest;
import com.LMS.LMS.DTOs.BorrowerRequest;
import com.LMS.LMS.Entities.Author;
import com.LMS.LMS.Entities.Borrower;
import com.LMS.LMS.Services.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/borrower")
@RequiredArgsConstructor
public class BorrowerController {
    private final BorrowerService borrowerService ;

    @PostMapping("/create")
    public ResponseEntity<String> createBorrower(@RequestBody BorrowerRequest borrowerRequest){
        return borrowerService.createBorrower(borrowerRequest);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateBorrower( @RequestBody Borrower borrower){
        return borrowerService.updateBorrower(borrower);
    }
    @GetMapping ("/get")
    public ResponseEntity<?> getBorrowerById( @RequestParam UUID id){
        return borrowerService.getBorrowerId(id);
    }
    @GetMapping ("/get_all")
    public List<Borrower> getAllBorrower(){
        return borrowerService.getAllBorrowers();
    }
    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteBorrowerById( @RequestParam UUID id){
        return borrowerService.deleteBorrower(id);
    }

}
