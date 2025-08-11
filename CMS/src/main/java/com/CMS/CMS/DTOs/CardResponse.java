package com.CMS.CMS.DTOs;

import com.CMS.CMS.Enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response object containing card details")
public class CardResponse {

    @Schema(description = "Card number", example = "1234-5678-9876-5432")
    private String cardNumber;

    @Schema(description = "Expiration date of the card", example = "2025-12-31")
    private LocalDate expirationDate;

    @Schema(description = "Current status of the card(ACTIVE/INACTIVE)", example = "ACTIVE")
    private StatusType Status;

}
