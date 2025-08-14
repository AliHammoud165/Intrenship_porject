package com.LMS.LMS.services.implementation;

import com.LMS.LMS.client.CMSClient;
import com.LMS.LMS.client.EmailClient;
import com.LMS.LMS.dtos.*;
import com.LMS.LMS.models.Book;
import com.LMS.LMS.models.Borrower;
import com.LMS.LMS.models.BorrowingTransactions;
import com.LMS.LMS.enums.TransactionType;
import com.LMS.LMS.repositories.BookRepository;
import com.LMS.LMS.repositories.BorrowerRepository;
import com.LMS.LMS.repositories.BorrowingTransactionRepository;
import com.LMS.LMS.services.inter.BorrowingTransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowingTransactionServiceImplementation implements BorrowingTransactionService {

    private final EmailProducerImplementation emailProducerImplementation;
    private final BorrowingTransactionRepository borrowingTransactionRepository;
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final BookServiceImplementation bookServiceImplementation;
    private final ModelMapper modelMapper;
    private final EmailClient emailClient;
    private final CMSClient cmsClient;
    private static final Logger logger = LoggerFactory.getLogger(BorrowingTransactionServiceImplementation.class);



    @Override
    public ResponseEntity<String> createBorrowingTransaction(BorrowingTransactionsBRequest request) {
        logger.info("Creating borrowing transaction for borrower email: {}, book ISBN: {}", request.getBorrowerEmail(), request.getBookISBN());

        Optional<List<Book>> books = bookRepository.findAllByIsbn(request.getBookISBN());
        Optional<Borrower> borrowerOptional = borrowerRepository.findByEmail(request.getBorrowerEmail());

        if (books.isEmpty() || borrowerOptional.isEmpty()) {
            logger.error("Book or borrower not found for ISBN: {}, email: {}", request.getBookISBN(), request.getBorrowerEmail());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book or Borrower not found");
        }

        if (books.get().isEmpty()) {
            logger.error("No book copies found for ISBN: {}", request.getBookISBN());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book or Borrower not found");
        }

        List<Book> availableBooks = getAvailableBooks(request.getBookISBN());
        if (availableBooks == null || availableBooks.isEmpty()) {
            logger.warn("No available book copy found for ISBN: {}", request.getBookISBN());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No available book copy found");
        }

        if (request.getBorrowDate() == null || request.getStatus() == null) {
            logger.error("Borrow date or status is missing");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Borrow date and status are required");
        }

        List<BorrowingTransactions> userTransactions = borrowingTransactionRepository.findAllByBorrowerEmail(request.getBorrowerEmail());
        if (userTransactions.size() >= 5) {
            logger.warn("Borrower {} has reached maximum allowed borrowed books", request.getBorrowerEmail());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You borrowed too much");
        }

        int days = getDaysBetween(request.getBorrowDate(), request.getReturnDate());

        BigDecimal amountToPay = books.get().get(0).getBasePrice()
                .add(books.get().get(0).getRatePrice().multiply(BigDecimal.valueOf(days)));



        CMSRequest cmsRequest = new CMSRequest(amountToPay, TransactionType.D, request.getCurrencyType(), request.getCardNb());
        CMS(cmsRequest);

        BorrowingTransactions transaction = modelMapper.map(request, BorrowingTransactions.class);
        transaction.setBook(availableBooks.get(0));
        transaction.setBorrower(borrowerOptional.get());
        transaction.setAmount(amountToPay);

        System.out.println(cmsRequest);

        BookUpdateRequest bookUpdateRequest = modelMapper.map(availableBooks.get(0), BookUpdateRequest.class);
        bookUpdateRequest.setAvailable(false);
        bookUpdateRequest.setAuthorId(availableBooks.get(0).getAuthor().getId());
        bookServiceImplementation.updateBook(bookUpdateRequest);

        borrowingTransactionRepository.save(transaction);
        logger.info("Borrowing transaction saved successfully for borrower {}", request.getBorrowerEmail());

        EmailRequest emailRequest = new EmailRequest(borrowerOptional.get().getEmail(),
                "Book Borrowed Successfully",
                "Book " + transaction.getBook().getTitle() + " borrowed successfully");
        notifyUserByEmail(emailRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("Borrowing transaction created successfully");
    }

    @Override
    public ResponseEntity<String> returnBorrowedBook(BorrowingTransactionsRRequest request) {
        logger.info("Processing return of book ISBN {} by borrower {}", request.getBookISBN(), request.getBorrowerEmail());

        Optional<List<Book>> books = bookRepository.findAllByIsbn(request.getBookISBN());
        Optional<Borrower> borrowerOptional = borrowerRepository.findByEmail(request.getBorrowerEmail());
        if (books.isEmpty() || borrowerOptional.isEmpty()) {
            logger.error("Book or Borrower not found for return, ISBN: {}, Email: {}", request.getBookISBN(), request.getBorrowerEmail());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book or Borrower not found");
        }

        List<BorrowingTransactions> userTransactions = borrowingTransactionRepository.findAllByBorrowerEmail(request.getBorrowerEmail());
        Optional<BorrowingTransactions> matchingTransaction = userTransactions.stream()
                .filter(tx -> "BORROWED".equalsIgnoreCase(tx.getStatus()))
                .filter(tx -> tx.getBook().getIsbn().equalsIgnoreCase(request.getBookISBN()))
                .findFirst();

        if (matchingTransaction.isEmpty()) {
            logger.error("No active borrowing transaction found for book ISBN {} and borrower {}", request.getBookISBN(), request.getBorrowerEmail());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Active borrowing transaction not found");
        }

        int days = getDaysBetween(matchingTransaction.get().getBorrowDate(), LocalDate.now());
        BigDecimal amountToPay = books.get().get(0).getRatePrice().multiply(BigDecimal.valueOf(days));

        CMSRequest cmsRequest = new CMSRequest(amountToPay, TransactionType.D, matchingTransaction.get().getCurrencyType(), matchingTransaction.get().getCardNb());
        CMS(cmsRequest);

        BorrowingTransactions transaction = matchingTransaction.get();
        Book borrowedBook = transaction.getBook();

        transaction.setStatus(request.getStatus() != null ? request.getStatus() : "returned");

        BookUpdateRequest bookDto = modelMapper.map(borrowedBook, BookUpdateRequest.class);
        bookDto.setAvailable(true);
        bookDto.setAuthorId(borrowedBook.getAuthor().getId());
        bookServiceImplementation.updateBook(bookDto);

        borrowingTransactionRepository.save(transaction);
        logger.info("Book returned successfully for borrower {}", request.getBorrowerEmail());

        return ResponseEntity.status(HttpStatus.OK).body("Book returned successfully");
    }

    @Override
    public List<Book> getAvailableBooks(String isbn) {
        logger.info("Fetching available books for ISBN: {}", isbn);
        return bookRepository.findByIsbnAndAvailableTrue(isbn);
    }
//
//    public void notifyUserByEmail(EmailRequest request) {
//        String response = emailClient.sendEmail(request);
//        logger.info("Email service response: {}", response);
//    }

    public void notifyUserByEmail(EmailRequest emailRequest) {
         ObjectMapper objectMapper = new ObjectMapper();

        try {
            String emailJson = objectMapper.writeValueAsString(emailRequest);
            emailProducerImplementation.sendEmailRequest(emailJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void CMS(CMSRequest cmsRequest) {
        logger.info("CMS service card number: {}", cmsRequest.getCardNb());
        String response = cmsClient.CmsHandler(cmsRequest);
        logger.info("CMS service response: {}", response);
    }

    public static int getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }
@Override
public ResponseEntity<String> deleteTransactionById(UUID id) {
        logger.info("Attempting to delete transaction with id: {}", id);

        if (!borrowingTransactionRepository.existsById(id)) {
            logger.warn("Transaction with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
        }

        borrowingTransactionRepository.deleteById(id);
        logger.info("Transaction with id {} deleted successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body("Transaction deleted successfully");
    }
    @Override
    public List<BorrowingTransactions> getAllTransactionsWithLogs() {
        logger.info("Fetching all transactions from the database");

        List<BorrowingTransactions> transactions = borrowingTransactionRepository.findAll();

        if (transactions.isEmpty()) {
            logger.warn("No transactions found in the database");
        } else {
            logger.info("Successfully retrieved {} transactions", transactions.size());
        }

        return transactions;
    }

}
