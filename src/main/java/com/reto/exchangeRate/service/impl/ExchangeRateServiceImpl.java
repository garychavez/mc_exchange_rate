package com.reto.exchangeRate.service.impl;

import com.reto.exchangeRate.dao.ExchangeRateDao;
import com.reto.exchangeRate.model.dto.ExchangeRateRequest;
import com.reto.exchangeRate.model.dto.ExchangeRateResponse;
import com.reto.exchangeRate.model.dto.RateRequest;
import com.reto.exchangeRate.model.dto.RateResponse;
import com.reto.exchangeRate.service.ExchangeRateService;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

  @Autowired
  private ExchangeRateDao exchangeRateDao;

  @Override
  public Maybe<ExchangeRateResponse> save(ExchangeRateRequest request) {
    log.info("Guardando datos del tipo de cambio");
    return exchangeRateDao.generateExchangeRate(request);
  }

  @Override
  public Maybe<RateResponse> saveRate(RateRequest request) {
    return exchangeRateDao.generateRate(request);
  }

  @Override
  public Maybe<RateResponse> updateRate(RateRequest request) {
    return exchangeRateDao.updateRate(request);
  }

  @Override
  public Maybe<RateResponse> searchRateCurrency(String currency) {
    log.info("Buscando el valor de la moneda");
    return exchangeRateDao.searchRateCurrency(currency);
  }

  @Override
  public Observable<ExchangeRateResponse> findAllExchangeRate() {
    return exchangeRateDao.findAllExchangeRate();
  }

  @Override
  public Observable<RateResponse> findAllRate() {
    return exchangeRateDao.findAllRate();
  }


}
