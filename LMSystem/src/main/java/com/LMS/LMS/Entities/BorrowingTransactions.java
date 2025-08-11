package com.LMS.LMS.Entities;

import com.LMS.LMS.Enums.CurrencyType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name="borrowing_transactions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BorrowingTransactions {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private Borrower borrower;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;
    @Column(name = "return_date")
    private LocalDate returnDate;
    @Column(name = "status")
    private String status;
    @Column(name="currency")
    private CurrencyType currencyType;
    @Column(name="amount")
    private BigDecimal amount;
    @Column(name="cardnb")
    private String Cardnb;

}
