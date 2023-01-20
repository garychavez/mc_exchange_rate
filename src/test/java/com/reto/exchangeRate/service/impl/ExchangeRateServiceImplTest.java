package com.reto.exchangeRate.service.impl;

import com.google.gson.Gson;
import com.reto.exchangeRate.dao.impl.ExchangeRateDaoImpl;
import com.reto.exchangeRate.model.dto.ExchangeRateRequest;
import com.reto.exchangeRate.model.dto.ExchangeRateResponse;
import com.reto.exchangeRate.model.dto.RateRequest;
import com.reto.exchangeRate.model.dto.RateResponse;
import com.reto.util.TestUtil;
import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import lombok.var;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceImplTest {
    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Mock
    private ExchangeRateDaoImpl exchangeRateDao;

    private static final Gson GSON = new Gson();

    private static final String CURRENCY = "eur";

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    @DisplayName("Guardar  los datos de un tipo de cambio")
    void returnExchangeRateResponseWhenRequestIsValidate() {
        ExchangeRateRequest exchangeRateRequest = GSON.fromJson(TestUtil.readJsonFileToString("mock/exchange_rate_response.json"),
                ExchangeRateRequest.class);
        ExchangeRateResponse exchangeRateResponse = GSON.fromJson(TestUtil.readJsonFileToString("mock/exchange_rate_response.json"),
                ExchangeRateResponse.class);

        when(exchangeRateDao.generateExchangeRate(any())).thenReturn(Maybe.just(exchangeRateResponse));
        TestObserver<ExchangeRateResponse> testObserver = exchangeRateService.save(exchangeRateRequest).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        var actual = testObserver.values().get(0);
        assertThat(actual.getRate()).isEqualTo(exchangeRateResponse.getRate());
    }

    @Test
    @DisplayName("Guardar  los datos de una moneda acorde al tipo")
    void returnRateResponseWhenRequestIsValidate() {
        RateRequest rateRequest = GSON.fromJson(TestUtil.readJsonFileToString("mock/rate_response.json"),
                RateRequest.class);
        RateResponse rateResponse = GSON.fromJson(TestUtil.readJsonFileToString("mock/rate_response.json"),
                RateResponse.class);

        when(exchangeRateDao.generateRate(any())).thenReturn(Maybe.just(rateResponse));
        TestObserver<RateResponse> testObserver = exchangeRateService.saveRate(rateRequest).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        var actual = testObserver.values().get(0);
        assertThat(actual.getRate()).isEqualTo(rateResponse.getRate());
    }

    @Test
    void updateRate() {
    }

    @Test
    @DisplayName("Obtiene  los datos de una moneda acorde al tipo")
    public void returnRateResponseWhenCurrencyIsValidate() {
        RateResponse rateResponse = GSON.fromJson(TestUtil.readJsonFileToString("mock/rate_response.json"),
                RateResponse.class);
        when(exchangeRateDao.searchRateCurrency(CURRENCY)).thenReturn(Maybe.just(rateResponse));
        TestObserver<RateResponse> testObserver = exchangeRateService.searchRateCurrency(CURRENCY).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        var actual = testObserver.values().get(0);
        assertThat(actual.getCurrency()).isEqualTo(CURRENCY);
    }

    @Test
    void findAllExchangeRate() {
    }

    @Test
    void findAllRate() {
    }
}