package com.LMS.LMS.models;

import com.LMS.LMS.enums.CategoryType;
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
public class Book {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name="isbn",nullable = false)
    private  String isbn;
    @Column(name="category",nullable = false)
    private CategoryType category;
    @Column(name = "availability",nullable = false)
    private boolean available;
    @Column(name = "base_price",nullable = false)
    private BigDecimal basePrice;
    @Column(name = "rate_price",nullable = false)
    private BigDecimal ratePrice;


    @ManyToOne
    @JoinColumn(name = "authorid", nullable = false)
    private Author author;
}
