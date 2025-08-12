package com.CMS.CMS.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Schema(description = "Request object for linking a card to an account")
public class CardAccountRequest {

    @Schema(description = "Unique ID of the card", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID cardID;

    @Schema(description = "Unique ID of the account", example = "9d1d0f7a-ccf7-4e55-b520-7158f9987db4")
    private UUID accountID;
}
