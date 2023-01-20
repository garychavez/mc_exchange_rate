package com.reto.exchangeRate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateResponse {

    private Long rateId;
    private String currency;
    private String currencyDescription;
    private BigDecimal rate;

}
