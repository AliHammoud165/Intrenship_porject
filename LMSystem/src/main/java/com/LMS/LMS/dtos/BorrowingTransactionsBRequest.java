package com.LMS.LMS.dtos;

import com.LMS.LMS.enums.CurrencyType;
import lombok.Data;

import java.time.LocalDate;

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
