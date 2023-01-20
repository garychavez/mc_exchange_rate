package com.reto.exchangeRate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateResponse {

    private Long exchangeRateId;
    private String originCurrency;
    private BigDecimal amount;
    private String destinationCurrency;
    private BigDecimal convertedAmount;
    private BigDecimal rate;


}
