package com.LMS.LMS.services.implementation;

import com.LMS.LMS.dtos.BorrowerRequest;
import com.LMS.LMS.dtos.BorrowerUpdateRequest;
import com.LMS.LMS.models.Borrower;
import com.LMS.LMS.models.BorrowingTransactions;
import com.LMS.LMS.repositories.BorrowerRepository;
import com.LMS.LMS.repositories.BorrowingTransactionRepository;
import com.LMS.LMS.services.inter.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BorrowerServiceImplementation implements BorrowerService {

    private final ModelMapper modelMapper;
    private final BorrowerRepository borrowerRepository;
    private static final Logger logger = LoggerFactory.getLogger(BorrowerServiceImplementation.class);
    private  final BorrowingTransactionRepository borrowingTransactionRepository;

    @Override
    public ResponseEntity<String> createBorrower(BorrowerRequest borrowerRequest) {
        logger.info("Creating borrower: {}", borrowerRequest);

        if (borrowerRequest.getEmail() == null || borrowerRequest.getName() == null || borrowerRequest.getPhoneNumber() == null) {
            logger.error("Borrower creation failed - missing required fields");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Borrower Name or Email or Phone Number Required");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(borrowerRequest.getEmail()).matches()) {
            logger.error("Invalid email format: {}", borrowerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid email format");
        }

        Borrower borrower = modelMapper.map(borrowerRequest, Borrower.class);
        borrowerRepository.save(borrower);

        logger.info("Borrower '{}' created successfully", borrower.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Borrower created successfully");
    }

    @Override
    public ResponseEntity<String> updateBorrower(BorrowerUpdateRequest borrowerUpdateRequest) {
        logger.info("Updating borrower with id: {}", borrowerUpdateRequest.getId());

        Optional<Borrower> optionalBorrower = borrowerRepository.findById(borrowerUpdateRequest.getId());
        if (optionalBorrower.isEmpty()) {
            logger.error("Borrower with id {} not found", borrowerUpdateRequest.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Borrower not found");
        }

        Borrower borrower = optionalBorrower.get();
        modelMapper.map(borrowerUpdateRequest, borrower);
        borrowerRepository.save(borrower);

        logger.info("Borrower with id '{}' updated successfully", borrower.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Borrower updated successfully");
    }

    @Override
    public ResponseEntity<?> getBorrowerId(UUID id) {
        logger.info("Fetching borrower with id: {}", id);

        Optional<Borrower> borrowerOptional = borrowerRepository.findById(id);
        if (borrowerOptional.isEmpty()) {
            logger.error("Borrower with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Borrower not found");
        }

        logger.info("Borrower with id {} found", id);
        return ResponseEntity.ok(borrowerOptional.get());
    }

    @Override
    public List<Borrower> getAllBorrowers() {
        logger.info("Fetching all borrowers");
        return borrowerRepository.findAll();
    }

    @Override
    public ResponseEntity<String> deleteBorrower(UUID id) {
        logger.info("Attempting to delete borrower with id: {}", id);

        Optional<Borrower> borrowerOptional = borrowerRepository.findById(id);
        if (borrowerOptional.isEmpty()) {
            logger.warn("Borrower with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Borrower not found");
        }

        Borrower borrower = borrowerOptional.get();

        List<BorrowingTransactions> transactions = borrowingTransactionRepository.findAllByBorrowerId(borrower.getId());
        if (!transactions.isEmpty()) {
            borrowingTransactionRepository.deleteAll(transactions);
            logger.info("Deleted {} transactions for borrower id {}", transactions.size(), borrower.getId());
        }

        borrowerRepository.deleteById(id);
        logger.info("Borrower with id {} deleted successfully", id);

        return ResponseEntity.ok("Borrower and their transactions deleted successfully");
    }

}
