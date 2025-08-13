package com.LMS.LMS.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import java.util.UUID;

@Table(name = "borrower")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Audited
@Schema(name = "Borrower", description = "Represents a person borrowing books from the library")
public class Borrower {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier of the borrower", example = "e1c3d4b5-6a7b-8c9d-0e1f-234567890abc")
    private UUID id;

    @Column(name = "name", nullable = false)
    @Schema(description = "Full name of the borrower", example = "John Doe")
    private String name;

    @Column(name = "email", nullable = false)
    @Schema(description = "Email address of the borrower", example = "johndoe@example.com")
    private String email;

    @Column(name = "phone_number", nullable = false)
    @Schema(description = "Phone number of the borrower", example = "+1-202-555-0123")
    private String phoneNumber;
}
