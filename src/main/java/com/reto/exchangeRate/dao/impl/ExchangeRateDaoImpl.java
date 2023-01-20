package com.reto.exchangeRate.dao.impl;

import com.reto.exchangeRate.dao.ExchangeRateDao;
import com.reto.exchangeRate.exceptions.ResourceNotFoundException;
import com.reto.exchangeRate.model.dto.ExchangeRateRequest;
import com.reto.exchangeRate.model.dto.ExchangeRateResponse;
import com.reto.exchangeRate.model.dto.RateRequest;
import com.reto.exchangeRate.model.dto.RateResponse;
import com.reto.exchangeRate.model.entity.ExchangeRateEntity;
import com.reto.exchangeRate.model.entity.RateEntity;
import com.reto.exchangeRate.repository.ExchangeRateRepository;
import com.reto.exchangeRate.repository.RateRepository;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;

@Service
@Data
@Slf4j
public class ExchangeRateDaoImpl implements ExchangeRateDao {
  @Autowired
  private RateRepository rateRepository;

  @Autowired
  private ExchangeRateRepository exchangeRateRepository;

  @Override
  public Maybe<ExchangeRateResponse> generateExchangeRate(ExchangeRateRequest request) {
    return Maybe.just(request.getDestinationCurrency())
            .filter(s -> !s.equals(request.getOriginCurrency()))
            .switchIfEmpty(Maybe
                    .error( new ResourceNotFoundException("ingresar otro tipo de destinationCurrency ")))
            .flatMap(s -> searchRateCurrency(s))
            .map(rateResponse -> toSave(request,rateResponse.getRate()))
            .map(exchangeRateEntity -> exchangeRateRepository.save(exchangeRateEntity))
            .map(ExchangeRateEntity::getExchangeRateId)
            .flatMap(aLong -> maybeExchangeRate(aLong))
            .map(this::getExchangeRate)
            .subscribeOn(Schedulers.io());
  }

  @Override
  public Observable<ExchangeRateResponse> findAllExchangeRate() {
    log.info("Se obtiene todos los tipos de cambio registrados");
    return Observable.fromIterable(exchangeRateRepository.findAll())
            .map(this::getExchangeRate)
            .switchIfEmpty(Observable
                    .error( new ResourceNotFoundException("No se encontraron registros")))
            .subscribeOn(Schedulers.io());
  }

  @Override
  public Maybe<RateResponse> searchRateCurrency(String currency) {
    log.info("Buscando el valor de la moneda");
    return searchRate(currency)
            .switchIfEmpty(Maybe
                    .error( new ResourceNotFoundException("Currency Rate no encontrado ")))
            .subscribeOn(Schedulers.io());
  }

  @Override
  public Maybe<RateResponse> generateRate(RateRequest request) {

    return Maybe.just(request)
            .filter(this::validate)
            .flatMap(this::saveRate)
            .switchIfEmpty(Maybe
                    .error( new ResourceNotFoundException("Moneda ya registrada previamente")))
            .subscribeOn(Schedulers.io());

  }


  @Override
  public Observable<RateResponse> findAllRate() {
    log.info("Se obtiene todos los montos de  tipos de cambio registrados");
    return Observable.fromIterable(rateRepository.findAll())
            .map(this::getRateCurrency)
            .switchIfEmpty(Observable
                    .error( new ResourceNotFoundException("No se encontraron registros")))
            .subscribeOn(Schedulers.io());
  }

  @Override
  public Maybe<RateResponse> updateRate(RateRequest request) {
    return searchDateRate(request.getRateId(),request.getCurrency())
            .switchIfEmpty(Maybe
                    .error( new ResourceNotFoundException("Datos de moneda no validos")))
            .map(entity -> toRequest(entity,request.getRate()))
            .map(this::toSaveRate)
            .map(rate -> rateRepository.save(rate))
            .map(this::getRateCurrency)
            .subscribeOn(Schedulers.io());

  }

  private RateResponse getRateCurrency(RateEntity model)  {
    log.info("Extrayendo registros de Rate");
    RateResponse r = new RateResponse();
      r.setRateId(model.getRateId());
      r.setCurrency(model.getCurrency());
      r.setCurrencyDescription(model.getCurrencyDescription());
      r.setRate(model.getRate());
      return r;
  }

  private ExchangeRateResponse getExchangeRate(ExchangeRateEntity model)  {
    log.info("Extrayendo registros de ExchangeRate");
    ExchangeRateResponse response = new ExchangeRateResponse();
    response.setExchangeRateId(model.getExchangeRateId());
    response.setOriginCurrency(model.getOriginCurrency());
    response.setAmount(model.getAmount());
    response.setDestinationCurrency(model.getDestinationCurrency());
    response.setConvertedAmount(model.getConvertedAmount());
    response.setRate(model.getRate());
    return response;
  }

  private ExchangeRateEntity toSave (ExchangeRateRequest request , BigDecimal rate) {
    log.info("Guardar registros de ExchangeRate");
    ExchangeRateEntity entity = new ExchangeRateEntity();
      entity.setExchangeRateId(request.getExchangeRateId());
      entity.setOriginCurrency(request.getOriginCurrency());
      entity.setAmount(request.getAmount());
      entity.setDestinationCurrency(request.getDestinationCurrency());
      entity.setConvertedAmount(generateConvertedAmount(request.getAmount(),rate));
      entity.setRate(rate);
      return entity;
  }
  private RateEntity toSaveRate (RateRequest request){
    log.info("Guardar registros de Rate");
    RateEntity entity = new RateEntity();
      entity.setRateId(request.getRateId());
      entity.setCurrency(request.getCurrency());
      entity.setCurrencyDescription(request.getCurrencyDescription());
      entity.setRate(request.getRate());
      return entity;
  }
  public BigDecimal generateConvertedAmount(BigDecimal amount,BigDecimal rate) {
    BigDecimal convertedAmount = new BigDecimal(0.0).setScale(2);
    convertedAmount = amount.multiply(rate).setScale(2);
    return convertedAmount;
  }

  private Maybe<ExchangeRateEntity> maybeExchangeRate(Long exchangeRateId){
    log.info("buscando por id y obteniendo los campos");
    return Maybe.just(
                    exchangeRateRepository.findById(exchangeRateId)
                            .<BadRequestException>orElseThrow(BadRequestException::new))
            .switchIfEmpty(Maybe.empty());
  }

  private Maybe<RateEntity> maybeRate(Long rateId){
    log.info("buscando por id y obteniendo los campos");
    return Maybe.just(
                    rateRepository.findById(rateId)
                            .<BadRequestException>orElseThrow(BadRequestException::new))
            .switchIfEmpty(Maybe.empty());
  }

  public Completable update(RateRequest request) {
    log.info("actualizando y guardando los campos");
    return maybeRate(request.getRateId())
            .map(operation -> generateRate(request)).ignoreElement();
  }

  public Maybe<RateEntity> searchDateRate(Long rateId , String currency) {

    return Observable.fromIterable(rateRepository.searchDateRateCurrency(rateId,currency.toLowerCase()))
            .filter(objClient -> objClient.getCurrency().equals(currency.toLowerCase()))
//            .map(this::getRateCurrency)
            .firstElement()
            .subscribeOn(Schedulers.io());
  }

  public Maybe<RateResponse> searchRate(String currency) {

    return Observable.fromIterable(rateRepository.searchRateCurrency(currency.toLowerCase()))
            .filter(objClient -> objClient.getCurrency().equals(currency.toLowerCase()))
            .map(this::getRateCurrency)
            .firstElement()
            .subscribeOn(Schedulers.io());
  }


  private boolean validate(RateRequest rateRequest) {
    return searchRate(rateRequest.getCurrency())
            .isEmpty().blockingGet();
  }

  public Maybe<RateResponse> saveRate(RateRequest request) {

    return Maybe.just(toSaveRate(request))
            .map(rate -> rateRepository.save(rate))
            .map(rate -> rate.getRateId())
            .flatMap(aLong -> maybeRate(aLong))
            .map(this::getRateCurrency)
            .subscribeOn(Schedulers.io())
            ;
  }
  public RateRequest toRequest(RateEntity entity,BigDecimal rate) {

    log.info("Extrayendo registros de Rate");
    RateRequest r = new RateRequest();
    r.setRateId(entity.getRateId());
    r.setCurrency(entity.getCurrency());
    r.setCurrencyDescription(entity.getCurrencyDescription());
    r.setRate(rate);
    return r;
  }

}
