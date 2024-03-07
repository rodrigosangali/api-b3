package com.sangali.apib3.entity;

import com.sangali.apib3.model.LinhaExcel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "extrato_negociacao")
@Entity(name = "ExtratoNegociacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ExtratoNegociacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataNegocio;

    private String tipoMovimentacao;

    private String cod_produto;

    private String instituicao;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal valorOperacao;

    public ExtratoNegociacao(LinhaExcel ativos) {

        this.dataNegocio = ativos.getDataOperacao();
        this.tipoMovimentacao = ativos.getTipoMovimentacao();
        this.cod_produto = ativos.getProduto();
        this.instituicao = ativos.getInstituicao();
        this.quantidade = ativos.getQuantidade();
        this.precoUnitario = ativos.getPrecoUnitario();
        this.valorOperacao = ativos.getValorOperacao();
    }
}