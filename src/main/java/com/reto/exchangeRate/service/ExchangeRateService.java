package com.reto.exchangeRate.service;

import com.reto.exchangeRate.model.dto.ExchangeRateRequest;
import com.reto.exchangeRate.model.dto.ExchangeRateResponse;
import com.reto.exchangeRate.model.dto.RateRequest;
import com.reto.exchangeRate.model.dto.RateResponse;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface ExchangeRateService {
    Maybe<ExchangeRateResponse> save(ExchangeRateRequest request);
    Maybe<RateResponse> saveRate(RateRequest request);

    Maybe<RateResponse> updateRate(RateRequest request);
    Maybe<RateResponse> searchRateCurrency(String currency);
    Observable<ExchangeRateResponse> findAllExchangeRate();
    Observable<RateResponse> findAllRate();
}
