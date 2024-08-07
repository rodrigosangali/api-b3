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


    @Query(value ="SELECT (SUM(CASE WHEN (t.tipoEvento LIKE \"Bought%\" OR t.tipoEvento LIKE \"STOCK%\") THEN t.quantidade ELSE 0 END) - SUM(CASE WHEN (t.tipoEvento LIKE \"sold%\" OR t.tipoEvento LIKE \"MANDATORY REVERSE%\") THEN t.quantidade ELSE 0 END)) as somaQuantidade, " +
                         "(SUM(CASE WHEN t.tipoEvento LIKE \"sold%\" THEN t.quantidade ELSE 0 END)) as somaQuantidadeVendida, " +
                         "(SUM(CASE WHEN (t.tipoEvento LIKE \"Bought%\" OR t.tipoEvento LIKE \"STOCK%\") THEN t.valorOperacao ELSE 0 END) )  as somaValorOperacao  " +
                 "FROM Transactions t where (t.tipoEvento LIKE \"Bought%\" OR t.tipoEvento LIKE \"STOCK%\" OR t.tipoEvento LIKE \"sold%\" OR t.tipoEvento LIKE \"MANDATORY REVERSE%\" ) AND t.produto = :produto ")
    Map<String, Object> obtemQuantidadeProduto(String  produto);

    @Query(value = "SELECT SUM(t.valorOperacao) from Transactions t where t.produto = :produto AND (t.tipoEvento LIKE \"%DIVIDEND%\" OR t.tipoEvento LIKE \"W-8 WITHHOLDING%\")")
    BigDecimal somaTotalProvento(String produto);


    // Consulta personalizada para verificar a existência de um registro pelo hash
    @Query(value = "SELECT COUNT(*) > 0 from Transactions t where t.hashTransaction = :hash")
    boolean existsByHash(String hash);
}
