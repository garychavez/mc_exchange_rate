package com.reto.exchangeRate.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "rate")
public class RateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateId;

    @Column(name = "currency")
    private String currency;

    @Column(name = "currency_description")
    private String currencyDescription;

    @Column(name = "rate")
    private BigDecimal rate;

}
