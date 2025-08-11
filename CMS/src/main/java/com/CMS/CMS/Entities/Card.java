package com.CMS.CMS.Entities;

import com.CMS.CMS.Enums.StatusType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Card entity containing card details and transactions")
@Table(name = "card")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Card {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Schema(description = "Unique card identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID Id;

    @Column(name = "status")
    @Schema(description = "Card status (ACTIVE/INACTIVE)", example = "ACTIVE")
    private StatusType Status;

    @Column(name = "expirydate")
    @Schema(description = "Card expiry date", example = "2025-12-31")
    private LocalDate ExpiryDate;

    @Column(name = "cardnumber")
    @Schema(description = "Card number", example = "6349532707369091")
    private  String CardNumber;


}

