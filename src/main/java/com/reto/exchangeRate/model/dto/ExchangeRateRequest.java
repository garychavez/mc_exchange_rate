package com.reto.exchangeRate.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("Model DetailRequest")
public class ExchangeRateRequest {

    @ApiModelProperty(value = "exchangeRateId", position = 1 , example = "1")
    private Long exchangeRateId;

    @ApiModelProperty(value = "originCurrency", position = 2, required = true , example = "soles")
    private String originCurrency;

    @ApiModelProperty(value = "amount", position = 3, required = true , example = "100")
    private BigDecimal amount;

    @ApiModelProperty(value = "destinationCurrency", position = 4, required = true , example = "usd")
    private String destinationCurrency;

}
