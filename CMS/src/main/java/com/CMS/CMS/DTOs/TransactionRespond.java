package com.CMS.CMS.DTOs;

import com.CMS.CMS.Enums.CurrencyType;
import com.CMS.CMS.Enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "Response object representing a transaction")
public class TransactionRespond {

    @Schema(description = "Transaction unique identifier", example = "a4d3f1bc-5678-4a2f-bd3e-678912345678")
    private String id;



    @Schema(description = "Amount involved in the transaction", example = "150.75")
    private BigDecimal TransactionAmount;

    @Schema(description = "Date and time when the transaction occurred", example = "2025-08-06T13:45:30")
    private LocalDateTime TransactionDate;

    @Schema(description = "Type of the transaction(C/D)", example = "C")
    private TransactionType TransactionType;

    @Schema(description = "Currency type used in the transaction(USD/LL)", example = "USD")
    private CurrencyType Currency;
}
