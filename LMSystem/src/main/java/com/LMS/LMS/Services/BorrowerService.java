package com.LMS.LMS.Services;

import com.LMS.LMS.DTOs.BorrowerRequest;
import com.LMS.LMS.Entities.Borrower;
import com.LMS.LMS.Repositories.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BorrowerService {
    private final BorrowerRepository borrowerRepository;
    public ResponseEntity<String> createBorrower (BorrowerRequest borrowerRequest){
        Borrower borrower = Borrower.builder()
                .Name( borrowerRequest.getName())
                .Email( borrowerRequest.getEmail())
                .PhoneNumber( borrowerRequest.getPhoneNumber())
                .build();
        if (borrower.getEmail()==null||borrower.getName()==null||borrower.getPhoneNumber()==null){

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Borrower Name or Email or Phone Number Required");

        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(borrower.getEmail()).matches()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid email format");
        }
        borrowerRepository.save(borrower);
        return ResponseEntity.status(HttpStatus.CREATED).body("Borrower created successfully");
    }

    public ResponseEntity<String> updateBorrower (Borrower request){
        Borrower borrower=borrowerRepository.getReferenceById(request.getId());
        borrower.setName(request.getName());
        borrower.setEmail(request.getEmail());
        borrower.setPhoneNumber(request.getPhoneNumber());
        borrowerRepository.save(borrower);
        return ResponseEntity.status(HttpStatus.OK).body("Borrower updated successfully");
    }
    public ResponseEntity<?> getBorrowerId(UUID id) {
        Optional<Borrower> borrowerOptional = borrowerRepository.findById(id);

        if (borrowerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Borrower not found");
        }

        return ResponseEntity.ok(borrowerOptional.get());
    }

    public List<Borrower> getAllBorrowers(){
        return
        borrowerRepository.getAllBorrowers();
    }

    public ResponseEntity<String> deleteBorrower(UUID id) {
        if (!borrowerRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Borrower not found");
        }
        borrowerRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Borrower deleted successfully");
    }
}
