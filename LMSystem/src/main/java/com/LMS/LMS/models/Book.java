package com.LMS.LMS.models;

import com.LMS.LMS.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "book")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Schema(name = "Book", description = "Represents a book in the library system")
public class Book {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier of the book", example = "b3e1c3d4-1a2b-4c56-9d2f-8b4d5e6f7a8b")
    private UUID id;

    @Column(name = "title", nullable = false)
    @Schema(description = "Title of the book", example = "Pride and Prejudice")
    private String title;

    @Column(name = "isbn", nullable = false)
    @Schema(description = "ISBN number of the book", example = "978-3-16-148410-0")
    private String isbn;

    @Column(name = "category", nullable = false)
    @Schema(description = "Category or genre of the book", example = "FICTION")
    private CategoryType category;

    @Column(name = "availability", nullable = false)
    @Schema(description = "Availability status of the book", example = "true")
    private boolean available;

    @Column(name = "base_price", nullable = false)
    @Schema(description = "Base price of the book", example = "12.99")
    private BigDecimal basePrice;

    @Column(name = "rate_price", nullable = false)
    @Schema(description = "Rental rate price of the book per day", example = "1.50")
    private BigDecimal ratePrice;

    @ManyToOne
    @JoinColumn(name = "authorid", nullable = false)
    @Schema(description = "Author of the book")
    private Author author;
}
