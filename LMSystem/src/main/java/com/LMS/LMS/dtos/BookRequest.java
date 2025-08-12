package com.LMS.LMS.dtos;

import com.LMS.LMS.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private String title;
    private  String isbn;
    private CategoryType category;
    private boolean available;
    private BigDecimal basePrice;
    private BigDecimal ratePrice;
}
