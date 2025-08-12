package com.CMS.CMS.dtos;

import com.CMS.CMS.enums.CurrencyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating an account")
public class AccountRequest {

    @Schema(description = "Currency type of the account(USD/LL)", example = "USD")
    private CurrencyType currency;

    @Schema(description = "Initial balance of the account", example = "1000.00")
    private BigDecimal balance;
}
