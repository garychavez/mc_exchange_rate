package com.reto.expose.web;

import com.reto.exchangeRate.model.dto.ExchangeRateRequest;
import com.reto.exchangeRate.model.dto.ExchangeRateResponse;
import com.reto.exchangeRate.model.dto.RateRequest;
import com.reto.exchangeRate.model.dto.RateResponse;
import com.reto.exchangeRate.service.ExchangeRateService;
import com.reto.exchangeRate.util.Constants;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Constants.MAIN_PATH)
@Api(tags = "Exchange Rate Microservice", description = "This microservice is in charge of managing the exchange rate.")
@Slf4j
public class ExchangeRateController {
    @Autowired
    ExchangeRateService exchangeRateService;

    @PostMapping
    @ApiOperation(value = Constants.SAVE_VALUE, notes = Constants.SAVE_NOTE)
    public Maybe<ExchangeRateResponse> saveExchangeRate(@RequestBody ExchangeRateRequest request) {
        log.info("Envio de parametros");
        return exchangeRateService.save(request);
    }

    @GetMapping
    @ApiOperation(value = Constants.SAVE_VALUE, notes = Constants.SAVE_NOTE)
    public Observable<ExchangeRateResponse> findAllExchangeRate() {
        log.info("Obtencion de todos los registros");
        return exchangeRateService.findAllExchangeRate();
    }

    @PostMapping(Constants.SAVE_RATE)
    @ApiOperation(value = Constants.SAVE_VALUE, notes = Constants.SAVE_NOTE)
    public Maybe<RateResponse> saveRate(@RequestBody RateRequest request) {
        log.info("Envio de parametros");
        return exchangeRateService.saveRate(request);
    }

    @GetMapping(Constants.SEARCH_RATE_CURRENCY)
    @ApiOperation(value = Constants.SAVE_VALUE, notes = Constants.SAVE_NOTE)
    public Maybe<RateResponse> searchRateCurrency(@PathVariable("currency")String currency) {
        log.info("Envio de parametros");
        return exchangeRateService.searchRateCurrency(currency);
    }

    @GetMapping(Constants.SEARCH_RATE)
    @ApiOperation(value = Constants.SAVE_VALUE, notes = Constants.SAVE_NOTE)
    public Observable<RateResponse> findAllRate() {
        log.info("Obtencion de todos los registros de cambios");
        return exchangeRateService.findAllRate();
    }

    @PutMapping(Constants.UPDATE_RATE)
    @ApiOperation(value = Constants.SAVE_VALUE, notes = Constants.SAVE_NOTE)
    public Maybe<RateResponse> updateRate(@RequestBody RateRequest request) {
        log.info("Actualizar Valor de moneda");
        return exchangeRateService.updateRate(request);
    }

}
