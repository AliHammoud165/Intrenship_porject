package com.LMS.LMS.dtos;

import com.LMS.LMS.enums.CurrencyType;
import com.LMS.LMS.enums.TransactionType;
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

    private String cardNb;

}
