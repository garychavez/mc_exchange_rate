package com.reto.exchangeRate.repository;

import com.reto.exchangeRate.model.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, Long> {

    @Query(" from RateEntity o where o.currency =:currency")
    List<RateEntity> searchRateCurrency(@Param("currency") String currency);

    @Query(" from RateEntity o where o.rateId =:rateId and o.currency =:currency")
    List<RateEntity> searchDateRateCurrency(@Param("rateId") Long rateId,@Param("currency") String currency);
}
