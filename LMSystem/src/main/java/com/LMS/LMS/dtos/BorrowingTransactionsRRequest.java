package com.LMS.LMS.dtos;

import lombok.Data;

@Data
public class BorrowingTransactionsRRequest {
    private String bookISBN;
    private String borrowerEmail;
    private String status;
}
