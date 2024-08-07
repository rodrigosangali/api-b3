package com.sangali.apib3.entity;

import com.sangali.apib3.model.LinhaExcel;
import com.sangali.apib3.utils.SHA1HasGenerator;
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

    private String hashTransaction;

    private LocalDate dataOperacao;

    private String produto;

    private String tipoEvento;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal valorOperacao;


    public Transactions(LinhaExcel transactions) {

        this.hashTransaction = SHA1HasGenerator.generatorSHA1Hash(transactions.getDataOperacao()
                        + transactions.getTipoMovimentacao()
                        + transactions.getProduto()
                        + transactions.getQuantidade()
                        + transactions.getPrecoUnitario()
                        + transactions.getValorOperacao());

        this.dataOperacao = transactions.getDataOperacao();
        this.tipoEvento = transactions.getTipoMovimentacao();
        this.produto = transactions.getProduto();
        this.quantidade = transactions.getQuantidade();
        this.precoUnitario = transactions.getPrecoUnitario();
        this.valorOperacao = transactions.getValorOperacao();
    }
}
