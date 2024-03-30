package com.sangali.apib3.entity;

import com.sangali.apib3.model.LinhaExcel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "extrato_negociacao")
@Entity(name = "ExtratoNegociacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class ExtratoNegociacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataNegocio;

    private String tipoMovimentacao;

    private String codProduto;

    private String instituicao;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal valorOperacao;

    public ExtratoNegociacao(LinhaExcel ativos) {

        this.dataNegocio = ativos.getDataOperacao();
        this.tipoMovimentacao = ativos.getTipoMovimentacao();
        this.codProduto = ativos.getProduto();
        this.instituicao = ativos.getInstituicao();
        this.quantidade = ativos.getQuantidade();
        this.precoUnitario = ativos.getPrecoUnitario();
        this.valorOperacao = ativos.getValorOperacao();
    }
}
