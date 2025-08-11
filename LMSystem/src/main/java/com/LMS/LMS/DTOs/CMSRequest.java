package com.LMS.LMS.DTOs;

import com.LMS.LMS.Enums.CurrencyType;
import com.LMS.LMS.Enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CMSRequest {

    private BigDecimal transactionAmount;

    private TransactionType transactionType;

    private CurrencyType currencyType;

    private String cardnb;

}
