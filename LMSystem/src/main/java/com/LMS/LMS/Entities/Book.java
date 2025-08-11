package com.LMS.LMS.Entities;

import com.LMS.LMS.Enums.CategoryType;
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
    private UUID Id;
    @Column(name = "title",nullable = false)
    private String Title;
    @Column(name="isbn",nullable = false)
    private  String ISBN;
    @Column(name="category",nullable = false)
    private CategoryType Category;
    @Column(name = "availability",nullable = false)
    private boolean Available;
    @Column(name = "base_price",nullable = false)
    private BigDecimal BasePrice;
    @Column(name = "rate_price",nullable = false)
    private BigDecimal RatePrice;


    @ManyToOne
    @JoinColumn(name = "authorid", nullable = false)
    private Author author;
}
