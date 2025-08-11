package com.CMS.CMS.Entities;

import com.CMS.CMS.Enums.CurrencyType;
import com.CMS.CMS.Enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Schema(description = "Transaction entity representing financial transactions")
@Table(name = "transaction")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier of the transaction", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID Id;
    @Column(name = "transactionamount")
    @Schema(description = "Amount involved in the transaction", example = "250.75")
    private BigDecimal TransactionAmount;
    @Column(name = "transactiondate")
    @Schema(description = "Date and time when the transaction occurred", example = "2025-08-06T14:30:00")
    private LocalDateTime TransactionDate;
    @Column(name = "transactiontype")
    @Schema(description = "Type of the transaction(D/C)", example = "C")
    private TransactionType TransactionType;
    @Column(name="currency")
    @Schema(description = "Currency used in the transaction(USD/LL)", example = "USD")
    private CurrencyType Currency;
    @ManyToOne
    @JoinColumn(name = "cardid")
    @Schema(description = "Card associated with the transaction, if applicable")
    private Card card;
}
