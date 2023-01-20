package com.reto.exchangeRate.repository;

import com.reto.exchangeRate.model.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository  extends JpaRepository<ExchangeRateEntity, Long> {
}
