package com.sangali.apib3.repository;

import com.sangali.apib3.entity.CurrentPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrentPriceRepository extends JpaRepository<CurrentPrice, String> {


    @Query(value ="SELECT t.valorOperacao  FROM CurrentPrice t where t.produto LIKE :produto% AND t.moeda = :moeda")
    BigDecimal getCurrentPrice(String  produto, String moeda);

    @Query(value ="SELECT t.valorOperacao  FROM CurrentPrice t where t.produto LIKE :produto AND t.moeda = :moeda")
    BigDecimal getCurrentPriceUSD(String  produto, String moeda);

}
