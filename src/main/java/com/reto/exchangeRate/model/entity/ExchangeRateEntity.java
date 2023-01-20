package com.reto.exchangeRate.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "exchange_rate")
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeRateId;

    @Column(name = "originCurrency")
    private String originCurrency;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "destinationCurrency")
    private String destinationCurrency;

    @Column(name = "convertedAmount")
    private BigDecimal convertedAmount;

    @Column(name = "rate")
    private BigDecimal rate;

}
