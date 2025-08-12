package com.LMS.LMS.dtos;

import com.LMS.LMS.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateRequest {
    private UUID id;
    private String title;
    private String isbn;
    private CategoryType category;
    private boolean available;
    private UUID authorId;
    private BigDecimal basePrice;
    private BigDecimal ratePrice;
}

