package com.CMS.CMS.DTOs;

import com.CMS.CMS.Enums.CurrencyType;
import com.CMS.CMS.Enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object for account details")
public class AccountResponse {

    @Schema(description = "Status of the account(ACTIVE/INACTIVE)", example = "ACTIVE")
    private StatusType Status;

    @Schema(description = "Currency type of the account", example = "USD")
    private CurrencyType Currency;

    @Schema(description = "Current balance of the account", example = "1500.00")
    private BigDecimal Balance;
}
