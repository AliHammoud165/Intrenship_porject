package com.LMS.LMS.DTOs;

import com.LMS.LMS.Enums.CurrencyType;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BorrowingTransactionsBRequest {
    private String cardNb;
    private String bookISBN;
    private String borrowerEmail;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status;
    private CurrencyType currencyType;
}
