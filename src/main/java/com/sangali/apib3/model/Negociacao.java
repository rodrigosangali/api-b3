package com.sangali.apib3.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor

public class Negociacao {

    private String codProduto;
    private BigDecimal valorOperacao;
    private BigDecimal precoMedio;
    private Integer quantidade;
    private BigDecimal totalProventos;

    public Negociacao(Negociacao negociacao) {
        this.codProduto = negociacao.codProduto;
        this.valorOperacao = negociacao.valorOperacao;
        this.precoMedio = negociacao.precoMedio;
        this.quantidade = negociacao.quantidade;
        this.totalProventos = negociacao.totalProventos;
    }
}
