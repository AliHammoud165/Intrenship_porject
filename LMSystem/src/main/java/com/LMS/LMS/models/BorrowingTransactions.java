package com.LMS.LMS.models;

import com.LMS.LMS.enums.CurrencyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "borrowing_transactions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@Schema(name = "BorrowingTransactions", description = "Represents a borrowing transaction between a borrower and the library")
public class BorrowingTransactions {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier of the borrowing transaction", example = "a3cbb2e4-7f16-4b1d-a23d-5a75f8b53c34")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @Schema(description = "Book being borrowed in this transaction")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    @Schema(description = "Borrower who initiated this transaction")
    private Borrower borrower;

    @Column(name = "borrow_date")
    @Schema(description = "Date the book was borrowed", example = "2025-08-12")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    @Schema(description = "Date the book was returned", example = "2025-09-01")
    private LocalDate returnDate;

    @Column(name = "status")
    @Schema(description = "Current status of the borrowing transaction", example = "BORROWED")
    private String status;

    @Column(name = "currency")
    @Schema(description = "Currency type used for the transaction amount")
    private CurrencyType currencyType;

    @Column(name = "amount")
    @Schema(description = "Amount charged for this transaction", example = "15.50")
    private BigDecimal amount;

    @Column(name = "cardnb")
    @Schema(description = "Card number used in the transaction", example = "1234-5678-9012-3456")
    private String cardNb;
}
