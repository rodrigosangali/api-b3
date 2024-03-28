package com.sangali.apib3.repository;


import com.sangali.apib3.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    @Query(value ="select distinct s.produto from Transactions s where s.produto <> \"\" ")
    List<String> findProduto();

    @Query(value ="SELECT SUM(t.quantidade) as somaQuantidade, SUM(t.valorOperacao) as somaValorOperacao  FROM Transactions t where t.tipoEvento LIKE \"Bought%\" AND t.produto = :produto ")
    Map<String, Object> obtemQuantidadeProduto(String  produto);

    @Query(value = "SELECT SUM(t.valorOperacao) from Transactions t where t.produto = :produto AND (t.tipoEvento LIKE \"%DIVIDEND%\" OR t.tipoEvento LIKE \"W-8 WITHHOLDING%\")")
    BigDecimal somaTotalProvento(String produto);
}
