package com.LMS.LMS.Services;

import com.LMS.LMS.Client.CMSClient;
import com.LMS.LMS.Client.EmailClient;
import com.LMS.LMS.DTOs.*;
import com.LMS.LMS.Entities.Book;
import com.LMS.LMS.Entities.Borrower;
import com.LMS.LMS.Entities.BorrowingTransactions;
import com.LMS.LMS.Enums.TransactionType;
import com.LMS.LMS.Repositories.BookRepository;
import com.LMS.LMS.Repositories.BorrowerRepository;
import com.LMS.LMS.Repositories.BorrowingTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowingTransactionService {
    private final BorrowingTransactionRepository borrowingTransactionRepository;
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final EmailClient emailClient;
    private final CMSClient cmsClient;


    public ResponseEntity<String> createBorrowingTransaction(BorrowingTransactionsBRequest request) {
       Optional <List<Book>> books = bookRepository.findByIsbn(request.getBookISBN());
        Optional<Borrower> borrowerOptional = borrowerRepository.findByEmail(request.getBorrowerEmail());

        if (books.isEmpty() || borrowerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book or Borrower not found");
        }

        if ( books.get().isEmpty() ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book or Borrower not found");
        }

        List <Book> availableBook=getAvailableBooks(request.getBookISBN());

        if (availableBook == null || availableBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No available book copy found");
        }

        if (request.getBorrowDate() == null || request.getStatus() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Borrow date and status are required");
        }

        List<BorrowingTransactions> userTransactions = borrowingTransactionRepository.findAllByBorrowerEmail(request.getBorrowerEmail());

        if (userTransactions.size()>=5){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You borrowed too much");
        }


        int days= getDaysBetween(request.getBorrowDate(),request.getReturnDate());

        BigDecimal amountToPay = books.get().get(0).getBasePrice().add(books.get().get(0).getRatePrice().multiply(BigDecimal.valueOf(days)));

        BorrowingTransactions transaction = BorrowingTransactions.builder()
                .book(availableBook.get(0))
                .borrower(borrowerOptional.get())
                .borrowDate(request.getBorrowDate())
                .returnDate(request.getReturnDate())
                .status(request.getStatus())
                .Cardnb(request.getCardNb())
                .amount(amountToPay)
                .currencyType(request.getCurrencyType())
                .build();






        CMSRequest cmsRequest=new CMSRequest(amountToPay, TransactionType.D,request.getCurrencyType(),request.getCardNb());

        CMS(cmsRequest);

        BookUpdateRequest bookUpdateRequest = modelMapper.map(availableBook.get(0), BookUpdateRequest.class);
        bookUpdateRequest.setAvailable(false);
        bookUpdateRequest.setAuthorId(availableBook.get(0).getAuthor().getId());
        bookService.updateBook(bookUpdateRequest);

        borrowingTransactionRepository.save(transaction);


        EmailRequest emailRequest = new EmailRequest(borrowerOptional.get().getEmail(),"Book Borrowed Successfully","Book " +transaction.getBook().getTitle()+ " borrowed successfully");

        notifyUserByEmail(emailRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("Borrowing transaction created successfully");
    }

    public ResponseEntity<String> returnBorrowedBook(BorrowingTransactionsRRequest request) {
        Optional<List<Book>> books = bookRepository.findByIsbn(request.getBookISBN());
        Optional<Borrower> borrowerOptional = borrowerRepository.findByEmail(request.getBorrowerEmail());
        if (books.isEmpty() || borrowerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book or Borrower not found");
        }


        List<BorrowingTransactions> userTransactions = borrowingTransactionRepository.findAllByBorrowerEmail(request.getBorrowerEmail());

        Optional<BorrowingTransactions> matchingTransaction = userTransactions.stream()
                .filter(tx -> "BORROWED".equalsIgnoreCase(tx.getStatus()))
                .filter(tx -> tx.getBook().getISBN().equalsIgnoreCase(request.getBookISBN()))
                .findFirst();

        if (matchingTransaction.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Active borrowing transaction not found");
        }

        int days= getDaysBetween(matchingTransaction.get().getBorrowDate(),LocalDate.now());

        BigDecimal amountToPay = (books.get().get(0).getRatePrice().multiply(BigDecimal.valueOf(days)));


        CMSRequest cmsRequest=new CMSRequest(amountToPay, TransactionType.D,matchingTransaction.get().getCurrencyType(),matchingTransaction.get().getCardnb());

        CMS(cmsRequest);


        BorrowingTransactions transaction = matchingTransaction.get();
        Book borrowedBook = transaction.getBook();

        transaction.setStatus(request.getStatus() != null ? request.getStatus() : "returned");

        BookUpdateRequest bookDto = modelMapper.map(borrowedBook, BookUpdateRequest.class);
        bookDto.setAvailable(true);
        bookDto.setAuthorId(borrowedBook.getAuthor().getId());
        bookService.updateBook(bookDto);

        borrowingTransactionRepository.save(transaction);

        return ResponseEntity.status(HttpStatus.OK).body("Book returned successfully");
    }

    public List<Book> getAvailableBooks(String isbn) {
        return bookRepository.findByIsbnAndAvailableTrue(isbn);
    }

    public void notifyUserByEmail(EmailRequest request) {
        String response = emailClient.sendEmail(request);
        System.out.println("Email service response: " + response);
    }

    public void CMS(CMSRequest cmsRequest) {
        String response = cmsClient.CmsHandler(cmsRequest);
        System.out.println("Email service response: " + response);
    }

    public static int getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }
}


