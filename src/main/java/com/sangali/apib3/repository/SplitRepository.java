package com.sangali.apib3.repository;

import com.sangali.apib3.entity.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public interface SplitRepository extends JpaRepository<Split, Long> {


    @Query(value ="SELECT t.dataSplit as dataSplit, t.multiplo as multiplo  FROM Split t where t.produto LIKE :produto%")
    Map<String, Object> obtemDataMultiploSplit(String  produto);

    @Query("UPDATE Split s SET s.process = :process where id = :id")
    void atualizarProcess(Long id, Boolean process);

}
