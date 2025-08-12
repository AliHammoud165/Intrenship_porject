package com.CMS.CMS.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of transaction: Credit or Debit")
public enum TransactionType {
    @Schema(description = "Credit transaction")
    C,

    @Schema(description = "Debit transaction")
    D
}
