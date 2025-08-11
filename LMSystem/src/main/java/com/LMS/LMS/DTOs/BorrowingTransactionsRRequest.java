package com.LMS.LMS.DTOs;

import com.LMS.LMS.Enums.CurrencyType;
import lombok.Data;

import java.time.LocalDate;
@Data
public class BorrowingTransactionsRRequest {
    private String bookISBN;
    private String borrowerEmail;
    private String status;
}
