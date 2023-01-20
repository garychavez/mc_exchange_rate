package com.reto.exchangeRate.dao.impl;

import com.google.gson.Gson;
import com.reto.exchangeRate.model.dto.ExchangeRateRequest;
import com.reto.exchangeRate.model.dto.ExchangeRateResponse;
import com.reto.exchangeRate.model.dto.RateResponse;
import com.reto.exchangeRate.model.entity.ExchangeRateEntity;
import com.reto.exchangeRate.model.entity.RateEntity;
import com.reto.exchangeRate.repository.ExchangeRateRepository;
import com.reto.exchangeRate.repository.RateRepository;
import com.reto.exchangeRate.service.impl.ExchangeRateServiceImpl;
import com.reto.util.TestUtil;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import lombok.var;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


@ExtendWith(MockitoExtension.class)
public class ExchangeRateDaoImplTest {

    @InjectMocks
    private ExchangeRateDaoImpl exchangeRateDao;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private RateRepository rateRepository;

    private static final Gson GSON = new Gson();

    private static final String CURRENCY = "eur";

    @Before
    public void setup(){
        initMocks(this);
    }

    @org.junit.Test
    @DisplayName("Guardar  los datos de un tipo de cambio")
    public void returnExchangeRateResponseWhenRequestIsValidate() {
        ExchangeRateRequest exchangeRateRequest = GSON.fromJson(TestUtil.readJsonFileToString("mock/exchange_rate_response.json"),
                ExchangeRateRequest.class);
        ExchangeRateResponse exchangeRateResponse = GSON.fromJson(TestUtil.readJsonFileToString("mock/exchange_rate_response.json"),
                ExchangeRateResponse.class);
        ExchangeRateEntity exchangeRateEntity = GSON.fromJson(TestUtil.readJsonFileToString("mock/exchange_rate_response.json"),
                ExchangeRateEntity.class);
        RateEntity rateEntity = GSON.fromJson(TestUtil.readJsonFileToString("mock/rate_response.json"),
                RateEntity.class);

        when(rateRepository.searchRateCurrency(exchangeRateRequest.getDestinationCurrency())).thenReturn(Arrays.asList(rateEntity));
//        when(rateRepository.searchRateCurrency(CURRENCY)).thenReturn(Arrays.asList(rateEntity));
        when(exchangeRateRepository.save(any())).thenReturn(Maybe.just(exchangeRateEntity));
//        when(exchangeRateRepository.save(any())).thenReturn(Observable.fromCallable(exchangeRateEntity));
//        when(exchangeRateRepository.save(any())).thenReturn(Arrays.asList(exchangeRateEntity));
        TestObserver<ExchangeRateResponse> testObserver = exchangeRateDao.generateExchangeRate(exchangeRateRequest).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        var actual = testObserver.values().get(0);
        assertThat(actual.getRate()).isEqualTo(exchangeRateResponse.getRate());
    }

    @Test
    void findAllExchangeRate() {
    }

    @Test
    void searchRateCurrency() {
    }

    @Test
    void generateRate() {
    }

    @Test
    void findAllRate() {
    }

    @Test
    void updateRate() {
    }
}