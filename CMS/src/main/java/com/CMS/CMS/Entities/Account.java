package com.CMS.CMS.Entities;

import com.CMS.CMS.Enums.CurrencyType;
import com.CMS.CMS.Enums.StatusType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Account entity representing a bank account with status, currency, and balance")
@Table(name = "account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier of the account", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID Id;

    @Column(name = "status")
    @Schema(description = "Card status (ACTIVE/INACTIVE)", example = "ACTIVE")
    private StatusType Status;

    @Column(name="currency")
    @Schema(description = "Currency type of the account(USD/LL)", example = "USD")
    private CurrencyType Currency;

    @Column(name="balance")
    @Schema(description = "Current balance of the account", example = "1000.50")
    private BigDecimal Balance;

}
