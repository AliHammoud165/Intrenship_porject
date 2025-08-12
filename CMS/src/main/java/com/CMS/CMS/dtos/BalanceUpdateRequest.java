package com.CMS.CMS.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(description = "Request object for updating account balance")
public class BalanceUpdateRequest {

    @Schema(description = "Unique identifier of the account", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "New balance to set for the account", example = "2500.00")
    private BigDecimal balance;
}
