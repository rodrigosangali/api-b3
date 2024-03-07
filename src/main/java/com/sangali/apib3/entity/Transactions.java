package com.sangali.apib3.entity;

import com.sangali.apib3.model.LinhaExcel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "transactions")
@Entity(name = "Transactions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String produto;

    private LocalDate dataPagamento;

    private String tipoEvento;

    private String instituicao;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal valorOperacao;


    public Transactions(LinhaExcel proventos) {
        this.dataPagamento = proventos.getDataOperacao();
        this.tipoEvento = proventos.getTipoMovimentacao();
        this.produto = proventos.getProduto();
        this.instituicao = proventos.getInstituicao();
        this.quantidade = proventos.getQuantidade();
        this.precoUnitario = proventos.getPrecoUnitario();
        this.valorOperacao = proventos.getValorOperacao();
    }
}
