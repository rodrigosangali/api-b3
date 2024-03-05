package com.sangali.apib3.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinhaExcel {
    private String tipoOperacao;

    private LocalDate dataOperacao;

    private String tipoMovimentacao;

    private String produto;

    private String instituicao;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal valorOperacao;
}
