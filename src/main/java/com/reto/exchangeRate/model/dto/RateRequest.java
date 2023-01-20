package com.reto.exchangeRate.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

@Data
@ApiModel("Model DetailRequest")
public class RateRequest {

    @ApiModelProperty(value = "rateId", position = 1 , example = "1")
    private Long rateId;

    @ApiModelProperty(value = "currency", position = 2, required = true , example = "usd")
    private String currency;

    @ApiModelProperty(value = "currencyDescription", position = 3, required = true , example = "dolares")
    private String currencyDescription;

    @ApiModelProperty(value = "rate", position = 4, required = true , example = "3.78")
    private BigDecimal rate;

}
