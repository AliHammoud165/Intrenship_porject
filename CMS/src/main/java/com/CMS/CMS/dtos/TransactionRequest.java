package com.CMS.CMS.dtos;

import com.CMS.CMS.enums.CurrencyType;
import com.CMS.CMS.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for creating a transaction")
public class TransactionRequest {

    @Schema(description = "Amount for the transaction", example = "100.50")
    private BigDecimal transactionAmount;

    @Schema(description = "Type of the transaction(C/D)", example = "D")
    private TransactionType transactionType;

    @Schema(description = "Currency type used in the transaction(USD/LL)", example = "USD")
    private CurrencyType currencyType;

    @Schema(description = "Number of the related card", example = "678912345678")
    private String cardNb;
}
