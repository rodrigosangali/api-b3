package com.sangali.apib3.repository;

import com.sangali.apib3.entity.ExtratoNegociacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExtratoNegociacaoRepository extends JpaRepository<ExtratoNegociacao, Long> {
    List<ExtratoNegociacao> findByCodProduto(String produto);

    @Query(value ="select s from ExtratoNegociacao s WHERE s.codProduto = :produto")
    List<ExtratoNegociacao> procuraPorProduto(String produto);


}
