package com.sangali.apib3.repository;

import com.sangali.apib3.entity.ExtratoNegociacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface ExtratoNegociacaoRepository extends JpaRepository<ExtratoNegociacao, Long> {
    List<ExtratoNegociacao> findByCodProduto(String produto);

    @Query(value ="select distinct s.codProduto from ExtratoNegociacao s")
    List<String>  consultaProdutos();

    @Query(value = "SELECT (SUM(CASE WHEN s.tipoMovimentacao = \"Compra\" THEN s.valorOperacao ELSE 0 END) - SUM(CASE WHEN s.tipoMovimentacao = \"Venda\" THEN s.valorOperacao ELSE 0 END) )  from ExtratoNegociacao s WHERE s.codProduto = :produto and s.instituicao  LIKE :instituicao% ")
    BigDecimal sumarizarPorProdutoInstituicao(String produto, String instituicao);

    @Query(value = "SELECT (SUM(CASE WHEN s.tipoMovimentacao = \"Compra\" THEN s.valorOperacao ELSE 0 END) - SUM(CASE WHEN s.tipoMovimentacao = \"Venda\" THEN s.valorOperacao ELSE 0 END)) /  (SUM(CASE WHEN s.tipoMovimentacao = \"Compra\" THEN s.quantidade ELSE 0 END) - SUM(CASE WHEN s.tipoMovimentacao = \"Venda\" THEN s.quantidade ELSE 0 END)) from ExtratoNegociacao s WHERE s.codProduto = :produto and s.instituicao  LIKE :instituicao% ")
    BigDecimal precoMedioPorProdutoInstituicao(String produto, String instituicao);

    @Query(value = "SELECT (SUM(CASE WHEN s.tipoMovimentacao = \"Compra\" THEN s.quantidade ELSE 0 END) - SUM(CASE WHEN s.tipoMovimentacao = \"Venda\" THEN s.quantidade ELSE 0 END)) from ExtratoNegociacao s WHERE s.codProduto = :produto and s.instituicao  LIKE :instituicao% ")
    Integer quantidadePorProdutoInstituicao(String produto, String instituicao);

    @Query(value = "SELECT (SUM(CASE WHEN s.tipoMovimentacao = \"Compra\" THEN s.quantidade ELSE 0 END) - SUM(CASE WHEN s.tipoMovimentacao = \"Venda\" THEN s.quantidade ELSE 0 END)) from ExtratoNegociacao s WHERE s.codProduto = :produto and s.instituicao  LIKE :instituicao% AND s.dataNegocio < :dataSplit")
    Integer obtemQuantidadeAtivos(String produto, String instituicao, LocalDate dataSplit);





}
