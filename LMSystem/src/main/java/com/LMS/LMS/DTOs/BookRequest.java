package com.LMS.LMS.DTOs;

import com.LMS.LMS.Entities.Author;
import com.LMS.LMS.Enums.CategoryType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private String Title;
    private  String ISBN;
    private CategoryType Category;
    private boolean Available;
    private BigDecimal BasePrice;
    private BigDecimal RatePrice;
}
