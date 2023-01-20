package com.reto.exchangeRate.dao;

import com.reto.exchangeRate.model.dto.ExchangeRateRequest;
import com.reto.exchangeRate.model.dto.ExchangeRateResponse;
import com.reto.exchangeRate.model.dto.RateRequest;
import com.reto.exchangeRate.model.dto.RateResponse;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface ExchangeRateDao {
    Maybe<ExchangeRateResponse> generateExchangeRate(ExchangeRateRequest request);
    Observable<ExchangeRateResponse> findAllExchangeRate();

    Maybe<RateResponse> searchRateCurrency(String currency);
    Maybe<RateResponse> generateRate(RateRequest request);
    Observable<RateResponse> findAllRate();

    Maybe<RateResponse> updateRate(RateRequest request);
}
