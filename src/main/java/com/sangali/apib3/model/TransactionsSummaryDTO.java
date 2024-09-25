package com.sangali.apib3.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TransactionsSummaryDTO {
    @JsonProperty("FromDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate dataInicio;

    @JsonProperty("ToDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate dataFim;

    @JsonProperty("TotalTransactionsAmount")
    private String totalTransacoes;

    @JsonProperty("BrokerageTransactions")
    private List<BrokerageTransactionDTO> transacoesCorretagem;

}
