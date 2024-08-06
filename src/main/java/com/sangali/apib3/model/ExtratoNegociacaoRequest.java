package com.sangali.apib3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
public class ExtratoNegociacaoRequest {

    private LocalDate dataNegocio;

    private String tipoMovimentacao;

    private String codProduto;

    private String instituicao;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal valorOperacao;

}
