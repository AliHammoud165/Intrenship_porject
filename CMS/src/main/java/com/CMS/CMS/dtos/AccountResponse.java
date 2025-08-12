package com.CMS.CMS.dtos;

import com.CMS.CMS.enums.CurrencyType;
import com.CMS.CMS.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object for account details")
public class AccountResponse {

    @Schema(description = "Status of the account(ACTIVE/INACTIVE)", example = "ACTIVE")
    private StatusType status;

    @Schema(description = "Currency type of the account", example = "USD")
    private CurrencyType currency;

    @Schema(description = "Current balance of the account", example = "1500.00")
    private BigDecimal balance;
}
