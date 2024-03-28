package com.sangali.apib3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Negociacao {

    private String codProduto;
    private BigDecimal valorOperacao;
    private BigDecimal precoMedio;
    private BigDecimal precoAtual = BigDecimal.ZERO;
    private Integer quantidade;
    private BigDecimal saldoAtual = BigDecimal.ZERO;
    private BigDecimal valorizacao;
    private BigDecimal totalProventos;
    private BigDecimal valorizacaoProventos;
    private BigDecimal totalValorizacao;
    private BigDecimal percentualCarteira;

    public Negociacao(Negociacao negociacao) {
        this.codProduto = negociacao.codProduto;
        this.valorOperacao = negociacao.valorOperacao;
        this.precoAtual = negociacao.precoAtual;
        this.precoMedio = negociacao.precoMedio;
        this.quantidade = negociacao.quantidade;
        this.saldoAtual = negociacao.saldoAtual;
        this.valorizacaoProventos = negociacao.valorizacaoProventos;
        this.totalProventos = negociacao.totalProventos;
        this.totalValorizacao = negociacao.totalValorizacao;
        this.percentualCarteira = negociacao.percentualCarteira;
    }
}
