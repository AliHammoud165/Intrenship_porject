package com.CMS.CMS.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Association entity linking a Card and an Account")
@Table(name = "card_account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class CardAccount {

    @Id
    @GeneratedValue
    @Schema(description = "Unique identifier of the card-account link", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cardid", nullable = false)
    @Schema(description = "Associated Card entity")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "accountid", nullable = false)
    @Schema(description = "Associated Account entity")
    private Account account;

}

