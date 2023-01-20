package com.reto.expose.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.reto.exchangeRate.model.dto.ExchangeRateRequest;
import com.reto.exchangeRate.model.dto.ExchangeRateResponse;
import com.reto.exchangeRate.model.dto.RateResponse;
import com.reto.exchangeRate.service.ExchangeRateService;
import com.google.gson.Gson;
import com.reto.util.TestUtil;
import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;

import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ExchangeRateControllerTest {
    @InjectMocks
    private ExchangeRateController exchangeRateController;
    @Mock
    private ExchangeRateService exchangeRateService;

    private static final Gson GSON = new Gson();

    private static final String CURRENCY = "eur";

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    @DisplayName("Obtiene  los datos de una moneda acorde al tipo")
    public void returnExchangeRateResponseWhenRequestIsValidate() {
        ExchangeRateRequest exchangeRateRequest = GSON.fromJson(TestUtil.readJsonFileToString("mock/exchange_rate_response.json"),
                ExchangeRateRequest.class);

        ExchangeRateResponse exchangeRateResponse = GSON.fromJson(TestUtil.readJsonFileToString("mock/rate_response.json"),
                ExchangeRateResponse.class);
        when(exchangeRateService.save(exchangeRateRequest)).thenReturn(Maybe.just(exchangeRateResponse));
        TestObserver<ExchangeRateResponse> testObserver = exchangeRateController.saveExchangeRate(exchangeRateRequest).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        var actual = testObserver.values().get(0);
        assertThat(actual.getRate()).isEqualTo(exchangeRateResponse.getRate());

    }

    @Test
    @DisplayName("Obtiene  los datos de una moneda acorde al tipo")
    public void returnRateResponseWhenCurrencyIsValidate() {
        RateResponse rateResponse = GSON.fromJson(TestUtil.readJsonFileToString("mock/rate_response.json"),
                RateResponse.class);
        when(exchangeRateService.searchRateCurrency(CURRENCY)).thenReturn(Maybe.just(rateResponse));
        TestObserver<RateResponse> testObserver = exchangeRateController.searchRateCurrency(CURRENCY).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        var actual = testObserver.values().get(0);
        assertThat(actual.getCurrency()).isEqualTo(CURRENCY);

    }
}